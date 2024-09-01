package com.scientisthamster.core.domain.run

import com.scientisthamster.core.domain.util.DataError
import com.scientisthamster.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface RunRepository {

    fun getRuns(): Flow<List<Run>>

    suspend fun fetchRuns(): EmptyResult<DataError>

    suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError>

    suspend fun deleteRun(id: String)
}