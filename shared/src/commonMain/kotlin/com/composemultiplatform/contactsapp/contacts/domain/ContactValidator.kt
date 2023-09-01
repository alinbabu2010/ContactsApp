package com.composemultiplatform.contactsapp.contacts.domain

object ContactValidator {

    fun validateContact(contact: Contact): ValidationResult {
        var result = ValidationResult()

        if (contact.firstName.isBlank()) {
            result = result.copy(firstNameError = "First name can't be empty")
        }

        if (contact.lastName.isBlank()) {
            result = result.copy(lastNameError = "Last name can't be empty")
        }

        val emailRegex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (!emailRegex.matches(contact.email)) {
            result = result.copy(emailError = "This is not a valid email")
        }

        val phoneNumberRegex =
            Regex("^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}\$")
        if (!phoneNumberRegex.matches(contact.phoneNumber)) {
            result = result.copy(phoneNumberError = "This is not a valid phone number")
        }

        return result
    }

    data class ValidationResult(
        val firstNameError: String? = null,
        val lastNameError: String? = null,
        val emailError: String? = null,
        val phoneNumberError: String? = null
    )

}