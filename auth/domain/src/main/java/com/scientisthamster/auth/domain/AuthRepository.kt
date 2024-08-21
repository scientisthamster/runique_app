package com.scientisthamster.auth.domain

import com.scientisthamster.core.domain.util.DataError
import com.scientisthamster.core.domain.util.EmptyResult

interface AuthRepository {

    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}