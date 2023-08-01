package xyz.katiedotson.camerabarcodestudy

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import com.google.mlkit.vision.barcode.common.Barcode

/**
 * A ViewModel for encapsulating the data for a QR Code, including the encoded data, the bounding
 * box, and the touch behavior on the QR Code.
 *
 * As is, this class only handles displaying the QR Code data if it's a URL. Other data types
 * can be handled by adding more cases of Barcode.TYPE_URL in the init block.
 */
class QrCodeViewModel(barcodes: List<Barcode>) {

    private val barcode = barcodes[0]

    var boundingRect: Rect = barcode.boundingBox!!
    var qrContent: String = ""
    var qrCodeTouchCallback = { _: View, _: MotionEvent -> false } //no-op

    init {
        when (barcode.valueType) {
            else -> {
                qrContent = "Unsupported data type: ${barcode.rawValue.toString()}"
            }
        }
    }
}