package com.mitch.appname.data.local

import androidx.room.RoomDatabase

// uncomment
/*@Database(
    // put entities inside the []
    entities = [],
    version = 1
)*/
abstract class AppDatabase : RoomDatabase() {
    // put DAOs here as abstract val
    // example -> abstract val dao: StockDao
}
