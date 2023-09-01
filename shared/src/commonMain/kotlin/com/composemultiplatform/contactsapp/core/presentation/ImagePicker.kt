package com.composemultiplatform.contactsapp.core.presentation

expect class ImagePicker {
    fun registerPicker(onImagePicked: (ByteArray) -> Unit)
    fun pickImage()
}