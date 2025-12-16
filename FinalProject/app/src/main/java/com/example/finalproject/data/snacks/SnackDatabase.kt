package com.example.finalproject.data.snacks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [SnackEntity::class],
    version = 2, // IMPORTANT: bumped version
    exportSchema = false
)
abstract class SnackDatabase : RoomDatabase() {

    abstract fun snackDao(): SnackDao

    companion object {
        @Volatile
        private var INSTANCE: SnackDatabase? = null

        fun getDatabase(context: Context): SnackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SnackDatabase::class.java,
                    "snacks_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(context))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}

/**
 * Populates the database ONCE when it is first created
 */
private class DatabaseCallback(
    private val context: Context
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            val database = SnackDatabase.getDatabase(context)
            val dao = database.snackDao()

            dao.insertAll(
                listOf(
                    SnackEntity(
                        name = "Popcorn",
                        description = "Butter popcorn",
                        price = 5.0
                    ),
                    SnackEntity(
                        name = "Soda",
                        description = "Refreshing soda",
                        price = 3.0
                    ),
                    SnackEntity(
                        name = "Candy",
                        description = "Sweet candy",
                        price = 2.5
                    )
                )
            )
        }
    }
}
