package com.composemultiplatform.contactsapp.contacts.presentation

import com.composemultiplatform.contactsapp.contacts.domain.Contact

data class ContactsListState(
    val contacts: List<Contact> = emptyList(),
    val recentlyAddedContacts: List<Contact> = emptyList(),
    val selectedContact: Contact? = null,
    val isSelectedContactSheetOpen: Boolean = false,
    val isAddContactSheetOpen: Boolean = false,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val phoneNumberError: String? = null
)
