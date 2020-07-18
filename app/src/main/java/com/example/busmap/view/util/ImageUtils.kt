package com.example.busmap.view.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class ImageUtils {
    companion object {
        fun bitmapDescriptorFromVector(
            context: Context,
            vectorResId: Int
        ): BitmapDescriptor? {
            val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
            vectorDrawable!!.setBounds(
                0,
                0,
                96,
                96
            )
            val bitmap = Bitmap.createBitmap(
                96,
                96,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}