package com.composemultiplatform.contactsapp.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.composemultiplatform.contactsapp.ui.theme.DarkColorScheme
import com.composemultiplatform.contactsapp.ui.theme.LightColorScheme
import com.composemultiplatform.contactsapp.ui.theme.Typography

@Composable
actual fun ContactsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )

}