
package com.yabaze.swipelibrary

import android.graphics.Bitmap
import android.graphics.Matrix
import java.io.ByteArrayOutputStream


class ImageUtils {

    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        try {
            val width = bm.width
            val height = bm.height
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            // CREATE A MATRIX FOR THE MANIPULATION
            val matrix = Matrix()
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight)
            // "RECREATE" THE NEW BITMAP
            //bm.recycle()
            return Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false
            )
        } catch (e:Exception){
            return null
        }
    }

    fun getResizedBitmap(bm: Bitmap, newWidth: Int): Bitmap? {
        val newHeight: Int
        val width = bm.width
        val height = bm.height
        val aspect_ratio = width / height.toDouble()
        newHeight = (newWidth * aspect_ratio).toInt()

        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        val resizedBitmap = Bitmap.createBitmap(
            bm, 0, 0, width, height,
            matrix, false
        )
        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return resizedBitmap
    }
}
