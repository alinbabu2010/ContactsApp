package com.composemultiplatform.contactsapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.composemultiplatform.contactsapp.contacts.presentation.ContactListScreen
import com.composemultiplatform.contactsapp.contacts.presentation.ContactsListViewModel
import com.composemultiplatform.contactsapp.core.presentation.ContactsTheme
import com.composemultiplatform.contactsapp.di.AppModule
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule
) {

    ContactsTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        val viewModel = getViewModel(
            key = Unit,
            factory = viewModelFactory {
                ContactsListViewModel(appModule.contactDataSource)
            }
        )
        val uiState by viewModel.uiState.collectAsState()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            ContactListScreen(
                state = uiState,
                newContact = viewModel.newContact,
                onEvent = viewModel::onEvent
            )

        }
    }

}