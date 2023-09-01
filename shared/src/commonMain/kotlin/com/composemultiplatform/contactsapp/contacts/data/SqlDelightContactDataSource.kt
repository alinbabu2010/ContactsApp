package com.composemultiplatform.contactsapp.contacts.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.composemultiplatform.contactsapp.contacts.domain.Contact
import com.composemultiplatform.contactsapp.contacts.domain.ContactDataSource
import com.composemultiplatform.contactsapp.contacts.util.toContact
import com.composemultiplatform.contactsapp.core.data.ImageStorage
import com.composemultiplatform.contactsapp.database.ContactDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock

class SqlDelightContactDataSource(
    database: ContactDatabase,
    private val imageStorage: ImageStorage
) : ContactDataSource {

    private val queries = database.contactsQueries

    override fun getContacts(): Flow<List<Contact>> {
        return queries.getContacts()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { contactEntities ->
                supervisorScope {
                    contactEntities.map {
                        async { it.toContact(imageStorage) }
                    }.map {
                        it.await()
                    }
                }
            }
    }

    override fun getRecentContacts(amount: Int): Flow<List<Contact>> {
        return queries.getRecentContacts(amount.toLong())
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { contactEntities ->
                supervisorScope {
                    contactEntities.map {
                        async { it.toContact(imageStorage) }
                    }.map {
                        it.await()
                    }
                }
            }
    }

    override suspend fun insertContact(contact: Contact) {
        val imagePath = contact.photoBytes?.let {
            imageStorage.saveImage(it)
        }
        queries.insertContactEntity(
            id = contact.id,
            firstName = contact.firstName,
            lastName = contact.lastName,
            phoneNumber = contact.email,
            email = contact.phoneNumber,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = imagePath
        )
    }

    override suspend fun deleteContact(id: Long) {
        val entity = queries.getContactsById(id).executeAsOne()
        entity.imagePath?.let {
            imageStorage.deleteImage(it)
        }
        queries.deleteContacEntity(id)
    }

}