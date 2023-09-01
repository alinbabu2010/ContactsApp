package com.composemultiplatform.contactsapp.contacts.util

import com.composemultiplatform.contactsapp.contacts.domain.Contact
import com.composemultiplatform.contactsapp.core.data.ImageStorage
import database.ContactEntity

suspend fun ContactEntity.toContact(imageStorage: ImageStorage): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        photoBytes = imagePath?.let { imageStorage.getImage(it) }
    )
}