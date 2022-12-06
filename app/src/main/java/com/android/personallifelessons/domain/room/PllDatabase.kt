package com.android.personallifelessons.domain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.personallifelessons.domain.models.LikedDislikedPll


@Database(
    entities = [LikedDislikedPll::class],
    version = 1
)
abstract class PllDatabase : RoomDatabase(){

    abstract fun likedDislikedDao() : LikedDislikedDao

    companion object{
        var INSTANCE: PllDatabase? = null
        fun getInstance(context: Context): PllDatabase {
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context, PllDatabase::class.java, "pll_db"
                ).fallbackToDestructiveMigration()
                    .build().also{
                        INSTANCE = it
                    }
            }
        }

    }

}