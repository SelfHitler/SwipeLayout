
package com.yabaze.swipelibrary

import android.graphics.Bitmap
import android.graphics.Matrix


class ImageUtils {

    //companion object {
        fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
            val width = bm.width
            val height = bm.height
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            // CREATE A MATRIX FOR THE MANIPULATION
            val matrix = Matrix()
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight)
            // "RECREATE" THE NEW BITMAP
            val resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false
            )
            bm.recycle()
            return resizedBitmap
        }
    //}
}
