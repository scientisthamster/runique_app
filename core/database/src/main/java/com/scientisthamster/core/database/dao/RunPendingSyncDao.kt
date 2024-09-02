package com.scientisthamster.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.scientisthamster.core.database.entity.DeletedRunSyncEntity
import com.scientisthamster.core.database.entity.RunPendingSyncEntity

@Dao
interface RunPendingSyncDao {

    /**
     * Methods for synchronizing the process of sending runs to the server
     */
    @Query("SELECT * FROM runpendingsyncentity WHERE userId=:userId")
    suspend fun getAllRunPendingSyncEntities(userId: String): List<RunPendingSyncEntity>

    @Query("SELECT * FROM runpendingsyncentity WHERE runId=:id")
    suspend fun getRunPendingSyncEntity(id: String): RunPendingSyncEntity?

    @Upsert
    suspend fun upsertPendingSyncEntity(entity: RunPendingSyncEntity)

    @Query("DELETE FROM runpendingsyncentity WHERE runId=:id")
    suspend fun deleteRunPendingSyncEntity(id: String)

    /**
     * Methods for synchronizing the process of deleting runs
     */
    @Query("SELECT * FROM deletedrunsyncentity WHERE userId=:userId")
    suspend fun getAllDeletedRunSyncEntities(userId: String): List<DeletedRunSyncEntity>

    @Upsert
    suspend fun upsertDeletedRunSyncEntity(entity: DeletedRunSyncEntity)

    @Query("DELETE FROM deletedrunsyncentity WHERE runId=:runId")
    suspend fun deleteDeletedRunSyncEntity(runId: String)
}