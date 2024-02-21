package com.mitch.appname.data.local.db

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
    // see example at: https://github.com/seve-andre/myUniBo/blob/main/app/src/main/kotlin/com/mitch/my_unibo/db/UniboDatabase.kt
    // abstract fun myDao(): MyDao
}
