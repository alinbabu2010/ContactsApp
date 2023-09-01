package com.composemultiplatform.contactsapp.contacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.composemultiplatform.contactsapp.contacts.domain.Contact
import com.composemultiplatform.contactsapp.contacts.domain.ContactDataSource
import com.composemultiplatform.contactsapp.contacts.domain.ContactValidator
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactsListViewModel(
    private val contactDataSource: ContactDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactsListState())
    val uiState = combine(
        _uiState,
        contactDataSource.getContacts(),
        contactDataSource.getRecentContacts(10)
    ) { state, contacts, recentContacts ->
        state.copy(
            contacts = contacts,
            recentlyAddedContacts = recentContacts
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        ContactsListState()
    )

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(event: ContactListEvent) {
        when (event) {
            ContactListEvent.DeleteContact -> deleteContact()
            ContactListEvent.DismissContact -> dismissContact()
            ContactListEvent.OnAddNewContactClick -> onAddNewContactClick()
            ContactListEvent.SaveContact -> saveContact()
            ContactListEvent.ClearSelectedContact -> clearSelectedContact()
            ContactListEvent.OnAddPhotoClicked -> {}
            is ContactListEvent.EditContact -> editContact(event.contact)
            is ContactListEvent.OnEmailChanged -> onEmailChanged(event.value)
            is ContactListEvent.OnFirstNameChanged -> onFirstNameChanged(event.value)
            is ContactListEvent.OnLastNameChanged -> onLastNameChanged(event.value)
            is ContactListEvent.OnPhoneNumberChanged -> onPhoneNumberChanged(event.value)
            is ContactListEvent.OnPhotoPicked -> onPhotoPicked(event.byteArray)
            is ContactListEvent.SelectContact -> selectContact(event.contact)
        }
    }

    private fun saveContact() {
        newContact?.let { contact ->
            val result = ContactValidator.validateContact(contact)
            val errors = listOfNotNull(
                result.firstNameError,
                result.lastNameError,
                result.emailError,
                result.phoneNumberError
            )

            if (errors.isEmpty()) {
                _uiState.update {
                    it.copy(
                        isAddContactSheetOpen = false,
                        firstNameError = null,
                        lastNameError = null,
                        emailError = null,
                        phoneNumberError = null
                    )
                }
                viewModelScope.launch {
                    contactDataSource.insertContact(contact)
                    clearNewContact()
                }
            } else {
                _uiState.update {
                    it.copy(
                        firstNameError = result.firstNameError,
                        lastNameError = result.lastNameError,
                        emailError = result.emailError,
                        phoneNumberError = result.phoneNumberError
                    )
                }
            }

        }
    }

    private fun selectContact(contact: Contact) {
        _uiState.update {
            it.copy(
                selectedContact = contact,
                isSelectedContactSheetOpen = true
            )
        }
    }

    private fun onPhotoPicked(value: ByteArray) {
        newContact = newContact?.copy(
            photoBytes = value
        )
    }

    private fun onFirstNameChanged(value: String) {
        newContact = newContact?.copy(
            firstName = value
        )
    }

    private fun onLastNameChanged(value: String) {
        newContact = newContact?.copy(
            lastName = value
        )
    }

    private fun onPhoneNumberChanged(value: String) {
        newContact = newContact?.copy(
            phoneNumber = value
        )
    }

    private fun onEmailChanged(value: String) {
        newContact = newContact?.copy(
            email = value
        )
    }

    private fun onAddNewContactClick() {
        _uiState.update {
            it.copy(
                isAddContactSheetOpen = true
            )
        }
        newContact = Contact(
            id = null,
            firstName = "",
            lastName = "",
            email = "",
            phoneNumber = "",
            photoBytes = null
        )
    }

    private fun editContact(contact: Contact) {
        _uiState.update {
            it.copy(
                isAddContactSheetOpen = true,
                isSelectedContactSheetOpen = false
            )
        }
        newContact = contact
    }

    private fun dismissContact() {
        // No need to invoke clear selected contact as it will be invoked on exit transition end.
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSelectedContactSheetOpen = false,
                    isAddContactSheetOpen = false,
                    firstNameError = null,
                    lastNameError = null,
                    emailError = null,
                    phoneNumberError = null
                )
            }
            clearNewContact()
        }
    }

    private fun deleteContact() {
        viewModelScope.launch {
            _uiState.value.selectedContact?.id?.let { id ->
                _uiState.update {
                    it.copy(isSelectedContactSheetOpen = false)
                }
                contactDataSource.deleteContact(id)
            }
        }
    }

    private suspend fun clearNewContact() {
        delay(100L) // Animation delay
        newContact = null
    }

    private fun clearSelectedContact() {
        _uiState.update {
            it.copy(selectedContact = null)
        }
    }

}