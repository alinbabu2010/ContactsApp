package com.composemultiplatform.contactsapp.core.presentation

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts


actual class ImagePicker(
    private val activity: ComponentActivity
) {

    private lateinit var getContent: ActivityResultLauncher<String>

    actual fun registerPicker(onImagePicked: (ByteArray) -> Unit) {
        getContent = activity.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                activity.contentResolver.openInputStream(uri)?.use {
                    onImagePicked(it.readBytes())
                }
            }
        }
    }

    actual fun pickImage() {
        getContent.launch("image/*")
    }

}