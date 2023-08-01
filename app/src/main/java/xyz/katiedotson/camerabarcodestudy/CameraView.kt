package xyz.katiedotson.camerabarcodestudy

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CameraView(
    cameraController: LifecycleCameraController,
    barcodesFlow: StateFlow<BarcodeModel>,
    torchEnabledFlow: StateFlow<Boolean>,
    onTorchButtonClicked: () -> Unit
) {

    val barcode: State<BarcodeModel> = barcodesFlow.collectAsState()
    val torchEnabled: State<Boolean> = torchEnabledFlow.collectAsState()

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

    Column(modifier = Modifier.padding(16.dp)) {

        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "Barcode: ${barcode.value.barcode}\n" +
                        "Type: ${barcode.value.barcodeType}\n" +
                        "Bounding Rect: $top, $right, $bottom, $left"
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = onTorchButtonClicked) {
            val text = if (torchEnabled.value) "Turn Off Torch" else "Turn On Torch"
            Text(text = text)
        }
    }
}

@Composable
@Preview
fun CameraViewPreview() {
    CameraView(
        cameraController = LifecycleCameraController(LocalContext.current),
        barcodesFlow = MutableStateFlow(BarcodeModel("12345", "EAN_13")),
        torchEnabledFlow = MutableStateFlow(false),
        onTorchButtonClicked = {}
    )
}
