package com.composemultiplatform.contactsapp.contacts.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.composemultiplatform.contactsapp.contacts.domain.Contact
import com.composemultiplatform.contactsapp.contacts.domain.ContactDataSource
import com.composemultiplatform.contactsapp.contacts.util.toContact
import com.composemultiplatform.contactsapp.database.ContactDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightContactDatabase(database: ContactDatabase) : ContactDataSource {

    private val queries = database.contactsQueries

    override fun getContacts(): Flow<List<Contact>> {
        return queries.getContacts()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map {
                it.map { contactEntity -> contactEntity.toContact() }
            }
    }

    override fun getRecentContacts(amount: Int): Flow<List<Contact>> {
        return queries.getRecentContacts(amount.toLong())
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map {
                it.map { contactEntity -> contactEntity.toContact() }
            }
    }

    override suspend fun insertContact(contact: Contact) {
        queries.insertContactEntity(
            id = contact.id,
            firstName = contact.firstName,
            lastName = contact.lastName,
            phoneNumber = contact.email,
            email = contact.phoneNumber,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = null
        )
    }

    override suspend fun deleteContact(id: Long) {
        queries.deleteContacEntity(id)
    }

}