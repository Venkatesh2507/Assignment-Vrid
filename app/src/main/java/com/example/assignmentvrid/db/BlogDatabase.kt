package com.example.assignmentvrid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.assignmentvrid.modal.Blog

@Database(
    entities = [Blog::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class BlogsDatabase : RoomDatabase(){

    abstract fun getBlogsDao(): BlogDAO

    companion object{
        @Volatile
        private var instance: BlogsDatabase?=null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it}
        }
        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                BlogsDatabase::class.java,
                "blogs_db.db"
            ).build()



    }


}