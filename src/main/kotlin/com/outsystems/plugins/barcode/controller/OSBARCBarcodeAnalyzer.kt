package com.outsystems.plugins.barcode.controller

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.lang.Exception

class OSBARCBarcodeAnalyzer(
    private val scanLibrary: String,
    private val onBarcodeScanned: (String) -> Unit,
    private val onScanningError: () -> Unit
): ImageAnalysis.Analyzer {

    companion object {
        private const val LOG_TAG = "OSBARCBarcodeAnalyzer"
    }

    override fun analyze(image: ImageProxy) {
        try {
            val scanningLib : OSBARCScanLibraryInterface = OSBARCScanLibraryFactory.createScanLibraryWrapper(scanLibrary)
            scanningLib.scanBarcode(
                image,
                {
                    onBarcodeScanned(it)
                },
                {
                    onScanningError
                }
            )
        } catch (e: Exception) {
            e.message?.let { Log.e(LOG_TAG, it) }
            onScanningError
        }
    }

}