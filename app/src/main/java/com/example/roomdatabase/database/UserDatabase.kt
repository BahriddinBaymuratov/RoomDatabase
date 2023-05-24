package com.example.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val dao: UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null

        operator fun invoke(context: Context): UserDatabase {
            return instance ?: synchronized(Any()) {
                instance ?: createDatabase(context).also {
                    instance = it
                }
            }
        }
        private fun createDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(
                context,
                UserDatabase::class.java,
                "User.db"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        }
    }
}