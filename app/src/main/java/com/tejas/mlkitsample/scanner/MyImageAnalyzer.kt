package com.tejas.mlkitsample.scanner

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage


class MyImageAnalyzer : ImageAnalysis.Analyzer {

    lateinit var callback: Callback

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            val options = BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
            val scanner = BarcodeScanning.getClient(options)
            scanner.process(inputImage).addOnSuccessListener { it2 ->
                imageProxy.close()
                if (it2.isNotEmpty()) {
                    readBarcodeData(it2)
                    scanner.close()
                }
            }
        }
    }

    private fun readBarcodeData(barcodes: List<Barcode>) {
        for (barcode in barcodes) {
            callback.saveBarcode(barcode)
            break
        }
    }

    interface Callback {
        fun saveBarcode(barcode: Barcode)
    }
}