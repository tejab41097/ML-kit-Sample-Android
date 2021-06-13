package com.tejas.mlkitsample.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.barcode.Barcode
import com.tejas.mlkitsample.MainActivity
import com.tejas.mlkitsample.R
import com.tejas.mlkitsample.databinding.FragmentScannerBinding
import com.tejas.mlkitsample.model.ScannedData
import com.tejas.mlkitsample.scanner.ImageAnalyzer
import com.tejas.mlkitsample.viewmodel.MainViewModel
import java.sql.Timestamp
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScannerFragment : Fragment(), ImageAnalyzer.Callback {

    private var binding: FragmentScannerBinding? = null
    private val fragmentBinding get() = binding!!

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var analyzer: ImageAnalyzer
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = (requireActivity() as MainActivity).mainViewModel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        else
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

        if (isCameraPermissionGranted())
            startScanner()
        else
            resultLauncher.launch(Manifest.permission.CAMERA)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it)
                startScanner()
            else
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_LONG).show()
        }

    private fun startScanner() {
        analyzer = ImageAnalyzer()
        analyzer.callback = this
        cameraExecutor = Executors.newSingleThreadExecutor()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                bindPreview(cameraProviderFuture.get())
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .build()
        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        preview.setSurfaceProvider(fragmentBinding.previewView.surfaceProvider)

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        imageAnalysis.setAnalyzer(cameraExecutor, analyzer)
        cameraProvider.bindToLifecycle(
            viewLifecycleOwner,
            cameraSelector,
            imageAnalysis,
            preview
        )
    }

    override fun saveBarcode(barcode: Barcode) {
        cameraExecutor.shutdownNow()
        mainViewModel.saveBarcodeData(
            ScannedData(
                0,
                resources.getStringArray(R.array.barcodes)[barcode.valueType],
                Timestamp(System.currentTimeMillis()).toString(),
                text1 = getText1(barcode),
                text2 = getText2(barcode)
            )
        )
        mainViewModel.syncData().observe(viewLifecycleOwner) {
            (requireActivity() as MainActivity).showSnackBar(getString(R.string.data_synced))
            findNavController().navigate(R.id.navigation_show_barcode_details)
        }
    }

    private fun getText1(barcode: Barcode): String? {
        return when (barcode.valueType) {
            Barcode.TYPE_CONTACT_INFO -> barcode.contactInfo?.name?.first + " " + barcode.contactInfo?.name?.last
            Barcode.TYPE_EMAIL -> barcode.email?.address
            Barcode.TYPE_PHONE -> barcode.phone?.number
            Barcode.TYPE_SMS -> barcode.sms?.phoneNumber
            Barcode.TYPE_TEXT -> barcode.rawValue
            Barcode.TYPE_URL -> barcode.url?.title
            Barcode.TYPE_WIFI -> barcode.wifi?.ssid
            Barcode.TYPE_GEO -> barcode.geoPoint?.lat.toString()
            else -> null
        }
    }

    private fun getText2(barcode: Barcode): String? {
        return when (barcode.valueType) {
            Barcode.TYPE_CONTACT_INFO -> if (barcode.contactInfo?.phones?.isNullOrEmpty() == false) barcode.contactInfo?.phones!![0].number else null
            Barcode.TYPE_EMAIL -> barcode.email?.subject + "<br/>" + barcode.email?.body
            Barcode.TYPE_PHONE -> barcode.phone?.type.toString()
            Barcode.TYPE_SMS -> barcode.sms?.message
            Barcode.TYPE_TEXT -> null
            Barcode.TYPE_URL -> barcode.url?.url
            Barcode.TYPE_WIFI -> barcode.wifi?.password
            Barcode.TYPE_GEO -> barcode.geoPoint?.lng.toString()
            else -> null
        }
    }

}