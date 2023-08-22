package com.composemultiplatform.contactsapp.contacts.util

import com.composemultiplatform.contactsapp.contacts.domain.Contact
import database.ContactEntity

fun ContactEntity.toContact(): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        photoBytes = null
    )
}