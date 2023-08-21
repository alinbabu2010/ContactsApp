package com.composemultiplatform.contactsapp.contacts.domain

data class Contact(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    @Suppress("ArrayInDataClass")
    val photoBytes: ByteArray?
)
