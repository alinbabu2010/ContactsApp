package com.composemultiplatform.contactsapp.contacts.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composemultiplatform.contactsapp.contacts.domain.Contact

@Composable
fun ContactListItem(
    contact: Contact,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ContactPhoto(
            contact = contact,
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = "${contact.firstName} ${contact.lastName}",
            modifier = Modifier.weight(1F)
        )
    }
}