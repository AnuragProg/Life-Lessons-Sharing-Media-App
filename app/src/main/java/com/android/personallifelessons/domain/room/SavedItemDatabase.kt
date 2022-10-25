package com.android.personallifelessons.domain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.personallifelessons.domain.model.PersonalLifeLessonRoomDto


@Database(
    entities = [ PersonalLifeLessonRoomDto::class ],
    version = 1
)
abstract class SavedItemDatabase: RoomDatabase() {

    abstract val savedItemDao: SavedItemDao

    companion object{
        private var INSTANCE : SavedItemDatabase? = null
        fun getInstance(context: Context): SavedItemDatabase = synchronized(this){
            return INSTANCE ?: Room.databaseBuilder(
                context, SavedItemDatabase::class.java, "saved_items_db"
            ).build().also{
                INSTANCE = it
            }
        }
    }
}