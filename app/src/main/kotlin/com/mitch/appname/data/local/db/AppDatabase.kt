package com.mitch.appname.data.local.db

import androidx.room.RoomDatabase

// uncomment
/*@Database(
    // put entities inside the []
    entities = [],
    version = 1
)*/
abstract class AppDatabase : RoomDatabase() {
    // put DAOs here as abstract val
    // see example at: https://github.com/seve-andre/myUniBo/blob/main/app/src/main/kotlin/com/mitch/my_unibo/db/UniboDatabase.kt
}
