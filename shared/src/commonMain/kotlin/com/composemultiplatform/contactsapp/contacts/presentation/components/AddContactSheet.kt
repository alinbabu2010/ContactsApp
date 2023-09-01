package com.composemultiplatform.contactsapp.contacts.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.composemultiplatform.contactsapp.contacts.domain.Contact
import com.composemultiplatform.contactsapp.contacts.presentation.ContactListEvent
import com.composemultiplatform.contactsapp.contacts.presentation.ContactsListState
import com.composemultiplatform.contactsapp.core.presentation.ContactBottomSheet

@Composable
fun AddContactSheet(
    state: ContactsListState,
    newContact: Contact?,
    isOpen: Boolean,
    modifier: Modifier = Modifier,
    onEvent: (ContactListEvent) -> Unit
) {

    ContactBottomSheet(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.height(44.dp))
                AddContactPhoto(newContact, onEvent)
                Spacer(Modifier.height(16.dp))
                ContactTextField(
                    value = newContact?.firstName ?: "",
                    placeholder = "First Name",
                    error = state.firstNameError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onValueChanged = {
                        onEvent(ContactListEvent.OnFirstNameChanged(it))
                    }
                )
                ContactTextField(
                    value = newContact?.lastName ?: "",
                    placeholder = "Last Name",
                    error = state.lastNameError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onValueChanged = {
                        onEvent(ContactListEvent.OnLastNameChanged(it))
                    }
                )
                ContactTextField(
                    value = newContact?.email ?: "",
                    placeholder = "Email",
                    error = state.emailError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    onValueChanged = {
                        onEvent(ContactListEvent.OnEmailChanged(it))
                    }
                )
                ContactTextField(
                    value = newContact?.phoneNumber ?: "",
                    placeholder = "Phone number",
                    error = state.phoneNumberError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    onValueChanged = {
                        onEvent(ContactListEvent.OnPhoneNumberChanged(it))
                    }
                )
                Button(
                    onClick = {
                        onEvent(ContactListEvent.SaveContact)
                    }
                ) {
                    Text("Save")
                }
            }
            IconButton(
                onClick = {
                    onEvent(ContactListEvent.DismissContact)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }

        }
    }

}

@Composable
private fun AddContactPhoto(
    newContact: Contact?,
    onEvent: (ContactListEvent) -> Unit
) {
    if (newContact?.photoBytes == null) {
        Box(modifier = Modifier.size(150.dp).clip(
            RoundedCornerShape(40)
        )
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                onEvent(ContactListEvent.OnAddPhotoClicked)
            }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(40)
            ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add Photo",
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(40.dp)
            )
        }
    } else {
        ContactPhoto(
            contact = newContact,
            modifier = Modifier.size(150.dp)
                .clickable {
                    onEvent(ContactListEvent.OnAddPhotoClicked)
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactTextField(
    value: String,
    placeholder: String,
    error: String?,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    onValueChanged: (String) -> Unit
) {

    Column(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(placeholder)
            },
            isError = error != null,
            supportingText = {
                if (error != null) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true,
            keyboardOptions = keyboardOptions,
        )
    }

}