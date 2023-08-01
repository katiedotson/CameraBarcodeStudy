package xyz.katiedotson.camerabarcodestudy

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CameraView(cameraController: LifecycleCameraController, barcodesFlow: StateFlow<BarcodeModel>) {

    val barcode: State<BarcodeModel> = barcodesFlow.collectAsState()

    AndroidView(factory = { context ->
        PreviewView(context).apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            scaleType = PreviewView.ScaleType.FILL_START
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            this.controller = cameraController
        }
    })

    val top = barcode.value.boundingRect?.top
    val right = barcode.value.boundingRect?.right
    val bottom = barcode.value.boundingRect?.bottom
    val left = barcode.value.boundingRect?.left


    Text(
        text = "Barcode: ${barcode.value.barcode}\n" +
                "Type: ${barcode.value.barcodeType}\n" +
                "Bounding Rect: $top, $right, $bottom, $left",
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    )
}
