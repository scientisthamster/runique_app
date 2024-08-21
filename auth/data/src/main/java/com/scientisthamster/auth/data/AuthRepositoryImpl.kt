package com.scientisthamster.auth.data

import com.scientisthamster.auth.domain.AuthRepository
import com.scientisthamster.core.data.networking.post
import com.scientisthamster.core.domain.util.DataError
import com.scientisthamster.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}