package com.example.seniordesign.room_database_swimdata

import android.content.Context
import android.os.Build.ID
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Dive::class), version = 1, exportSchema = false)
abstract class DiveRoomDatabase : RoomDatabase() {

    abstract fun diveDao(): DiveDao

    private class DiveDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var diveDao = database.diveDao()

                    // Add sample dive.

                    // Method 1
            //        var dive = Dive(1, "2", "3", "4", "5", "6", "7")
            //        diveDao.insert(dive)

                    //Method 2
                    diveDao.insert(Dive(6, "3", "60", "2345", "12123", "2/1/20", "7557"))
                    diveDao.insert(Dive(7, "4", "120", "2345", "12123", "2/19/20", "7557"))
                    diveDao.insert(Dive(8, "15", "855", "2345", "12123", "1/18/20", "7557"))
                    diveDao.insert(Dive(9, "1", "46", "2345", "12123", "3/1/20", "7557"))


                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DiveRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): DiveRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiveRoomDatabase::class.java,
                    "dive_database"
                )
                    .addCallback(DiveDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}