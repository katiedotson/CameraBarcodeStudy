package xyz.katiedotson.camerabarcodestudy

import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CameraScanViewModel: ViewModel() {

    val barcodesFlow: StateFlow<BarcodeModel> get() = _barcodesFlow
    private val _barcodesFlow = MutableStateFlow(BarcodeModel("", ""))

    fun barcodesDetected(barcodeResults: MutableList<Barcode>?) {
        barcodeResults?.let {
            val barcode = it[0]
            val barcodeModel = BarcodeModel(barcode.rawValue ?: "", barcode.valueType.toString(), barcode.boundingBox)
            _barcodesFlow.value = barcodeModel
        }
    }
}