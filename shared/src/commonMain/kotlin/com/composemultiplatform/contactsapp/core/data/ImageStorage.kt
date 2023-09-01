package com.composemultiplatform.contactsapp.core.data

expect class ImageStorage {
    suspend fun saveImage(byteArray: ByteArray): String
    suspend fun getImage(fileName: String): ByteArray?
    suspend fun deleteImage(fileName: String)
}