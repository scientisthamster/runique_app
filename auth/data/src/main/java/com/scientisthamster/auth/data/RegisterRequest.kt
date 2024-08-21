package com.scientisthamster.auth.data

import kotlinx.serialization.Serializable

@Serializable
internal data class RegisterRequest(
    val email: String,
    val password: String
)
