package com.composemultiplatform.contactsapp.di

import com.composemultiplatform.contactsapp.contacts.data.SqlDelightContactDataSource
import com.composemultiplatform.contactsapp.contacts.domain.ContactDataSource
import com.composemultiplatform.contactsapp.core.data.DatabaseDriverFactory
import com.composemultiplatform.contactsapp.core.data.ImageStorage
import com.composemultiplatform.contactsapp.database.ContactDatabase

actual class AppModule {
    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            database = ContactDatabase(
                driver = DatabaseDriverFactory().create()
            ),
            imageStorage = ImageStorage()
        )
    }
}