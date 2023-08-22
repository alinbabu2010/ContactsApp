package com.composemultiplatform.contactsapp.contacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.composemultiplatform.contactsapp.contacts.domain.Contact
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactsListViewModel : ViewModel() {

    private val contacts = (1..50).map {
        Contact(
            id = it.toLong(),
            firstName = "First$it",
            lastName = "Last$it",
            email = "first$it@mail.com",
            phoneNumber = "15362548$it",
            photoBytes = null
        )
    }

    private val _uiState = MutableStateFlow(
        ContactsListState(
            contacts = contacts
        )
    )
    val uiState = _uiState.asStateFlow()

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(contactListEvent: ContactListEvent) {

    }


}