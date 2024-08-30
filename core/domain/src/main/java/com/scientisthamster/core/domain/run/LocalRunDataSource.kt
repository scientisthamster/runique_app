package com.scientisthamster.core.domain.run

import com.scientisthamster.core.domain.util.DataError
import com.scientisthamster.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LocalRunDataSource {

    suspend fun upsertRun(run: Run): Result<String, DataError.Local>

    suspend fun upsertRuns(runs: List<Run>): Result<List<String>, DataError.Local>

    fun getRuns(): Flow<List<Run>>

    suspend fun deleteRun(id: String)

    suspend fun deleteAllRuns()
}