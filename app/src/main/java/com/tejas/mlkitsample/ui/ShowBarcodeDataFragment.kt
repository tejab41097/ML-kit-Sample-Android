package com.tejas.mlkitsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tejas.mlkitsample.MainActivity
import com.tejas.mlkitsample.R
import com.tejas.mlkitsample.databinding.FragmentShowBarcodeDataBinding


class ShowBarcodeDataFragment : Fragment() {

    private var binding: FragmentShowBarcodeDataBinding? = null
    private val fragmentBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowBarcodeDataBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBinding.root.setOnClickListener {
            findNavController().popBackStack()
        }
        val mainViewModel = (requireActivity() as MainActivity).mainViewModel
        mainViewModel.getScannedData().observe(viewLifecycleOwner) { item ->
            fragmentBinding.tvText1.isVisible = !item.text1.isNullOrEmpty()
            fragmentBinding.tvText1Title.isVisible = !item.text1.isNullOrEmpty()
            fragmentBinding.tvText2.isVisible = !item.text2.isNullOrEmpty()
            fragmentBinding.tvText2Title.isVisible = !item.text2.isNullOrEmpty()
            val barcodes = resources.getStringArray(R.array.barcodes)
            fragmentBinding.tvText1Title.text = getText1(item.type, barcodes)
            fragmentBinding.tvText2Title.text = getText2(item.type, barcodes)
            fragmentBinding.tvText1.text = item.text1
            fragmentBinding.tvText2.text = item.text2
            fragmentBinding.tvType.text = item.type
            fragmentBinding.tvTextSyncTitle.text = if (item.isSynced)
                getString(R.string.synced_with_server)
            else
                getString(R.string.not_synced_with_server)
        }
    }

    private fun getText1(type: String, barcodes: Array<String>): String? {
        return when (type) {
            barcodes[1] -> getString(R.string.name)
            barcodes[2] -> getString(R.string.email)
            barcodes[4] -> getString(R.string.name_number)
            barcodes[6] -> getString(R.string.number)
            barcodes[7] -> getString(R.string.text)
            barcodes[8] -> getString(R.string.title)
            barcodes[9] -> getString(R.string.ssid)
            barcodes[10] -> getString(R.string.latitude)
            else -> null
        }
    }

    private fun getText2(type: String, barcodes: Array<String>): String? {
        return when (type) {
            barcodes[1] -> getString(R.string.number)
            barcodes[2] -> getString(R.string.full_mail)
            barcodes[4] -> getString(R.string.type2)
            barcodes[6] -> getString(R.string.message)
            barcodes[7] -> null
            barcodes[8] -> getString(R.string.url)
            barcodes[9] -> getString(R.string.password)
            barcodes[10] -> getString(R.string.longitude)
            else -> null
        }
    }
}