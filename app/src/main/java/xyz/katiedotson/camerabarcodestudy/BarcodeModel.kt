package xyz.katiedotson.camerabarcodestudy

import android.graphics.Rect

data class BarcodeModel(val barcode: String, val barcodeType: String, val boundingRect: Rect? = null)
