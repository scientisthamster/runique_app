package com.scientisthamster.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scientisthamster.core.database.dao.RunDao
import com.scientisthamster.core.database.entity.RunEntity

@Database(
    entities = [RunEntity::class],
    version = 1
)
internal abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao
}