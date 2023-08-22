package com.composemultiplatform.contactsapp.di

import com.composemultiplatform.contactsapp.contacts.domain.ContactDataSource

expect class AppModule {
    val contactDataSource: ContactDataSource
}