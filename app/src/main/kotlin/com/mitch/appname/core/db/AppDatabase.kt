package com.mitch.appname.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

// see at https://developer.android.com/training/data-storage/room#database
@Database(
    entities = [
        // MyEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    // abstract fun myDao(): MyDao
}
