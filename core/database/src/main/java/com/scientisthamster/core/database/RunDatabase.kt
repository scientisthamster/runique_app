package com.scientisthamster.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scientisthamster.core.database.dao.RunDao
import com.scientisthamster.core.database.dao.RunPendingSyncDao
import com.scientisthamster.core.database.entity.DeletedRunSyncEntity
import com.scientisthamster.core.database.entity.RunEntity
import com.scientisthamster.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [
        RunEntity::class,
        RunPendingSyncEntity::class,
        DeletedRunSyncEntity::class
    ],
    version = 1
)
internal abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
}