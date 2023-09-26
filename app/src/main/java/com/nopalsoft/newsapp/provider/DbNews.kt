package com.nopalsoft.newsapp.provider

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nopalsoft.newsapp.model.News
import com.nopalsoft.newsapp.model.NewsDao

@Database(entities = [News::class], version = 1)
abstract class DbNews : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object{
        @Volatile
        private var INSTANCE: DbNews? = null

        fun getInstance(context: Context): DbNews{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DbNews::class.java,
                        "news_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}