package com.scientisthamster.core.database

import android.database.sqlite.SQLiteFullException
import com.scientisthamster.core.database.dao.RunDao
import com.scientisthamster.core.database.entity.RunEntity
import com.scientisthamster.core.database.mapper.toRun
import com.scientisthamster.core.database.mapper.toRunEntity
import com.scientisthamster.core.domain.run.LocalRunDataSource
import com.scientisthamster.core.domain.run.Run
import com.scientisthamster.core.domain.util.DataError
import com.scientisthamster.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RoomLocalRunDataSource(
    private val runDao: RunDao
) : LocalRunDataSource {

    override suspend fun upsertRun(run: Run): Result<String, DataError.Local> {
        return try {
            val runEntity = run.toRunEntity()
            runDao.upsertRun(runEntity)
            Result.Success(runEntity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(runs: List<Run>): Result<List<String>, DataError.Local> {
        return try {
            val runEntities = runs.map(Run::toRunEntity)
            runDao.upsertRuns(runEntities)
            Result.Success(runEntities.map(RunEntity::id))
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns().map { it.map(RunEntity::toRun) }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}