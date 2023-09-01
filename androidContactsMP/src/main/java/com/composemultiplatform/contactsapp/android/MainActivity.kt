package com.composemultiplatform.contactsapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.composemultiplatform.contactsapp.App
import com.composemultiplatform.contactsapp.core.presentation.ImagePickerFactory
import com.composemultiplatform.contactsapp.di.AppModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true,
                appModule = AppModule(this),
                imagePicker = ImagePickerFactory().createPicker()
            )
        }
    }
}