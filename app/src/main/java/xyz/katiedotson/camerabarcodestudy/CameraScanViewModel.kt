package xyz.katiedotson.camerabarcodestudy

import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CameraScanViewModel: ViewModel() {

    val barcodesFlow: StateFlow<BarcodeModel> get() = _barcodesFlow
    private val _barcodesFlow = MutableStateFlow(BarcodeModel("", ""))

    val torchFlow: StateFlow<Boolean> get() = _torchFlow
    private val _torchFlow = MutableStateFlow(false)

    fun barcodesDetected(barcodeResults: List<Barcode>) {
        val barcode = barcodeResults[0]
        val barcodeModel = BarcodeModel(
            barcode = barcode.rawValue ?: "",
            barcodeType = barcode.valueType.toString(),
            boundingRect = barcode.boundingBox
        )
        _barcodesFlow.value = barcodeModel
    }

    fun updateTorchEnabled() {
        _torchFlow.value = !_torchFlow.value
    }
}