package com.composemultiplatform.contactsapp.core.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

actual class ImageStorage(
    private val context: Context
) {

    actual suspend fun saveImage(byteArray: ByteArray): String {
        return withContext(Dispatchers.IO) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(byteArray)
            }
            fileName
        }
    }

    actual suspend fun getImage(fileName: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            context.openFileInput(fileName).use {
                it.readBytes()
            }
        }
    }

    actual suspend fun deleteImage(fileName: String) {
        return withContext(Dispatchers.IO) {
            context.deleteFile(fileName)
        }
    }

}