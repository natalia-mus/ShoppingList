package com.example.shoppinglist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import java.io.ByteArrayOutputStream

object ImageUtils {

    fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun getImageAsBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun getImageAsByteArray(drawable: Drawable?): ByteArray {
        val bitmap = (drawable as BitmapDrawable).bitmap
        return getBitmapAsByteArray(bitmap)
    }

    fun getImageAsByteArray(context: Context, uri: Uri): ByteArray {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        return getBitmapAsByteArray(bitmap)
    }

    fun getImageAsDrawable(context: Context, byteArray: ByteArray): BitmapDrawable {
        val bitmap = getImageAsBitmap(byteArray)
        return BitmapDrawable(context.resources, bitmap)
    }
}