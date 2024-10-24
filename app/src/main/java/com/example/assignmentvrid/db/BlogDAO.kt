package com.example.assignmentvrid.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignmentvrid.modal.Blog

@Dao
interface BlogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blog: Blog)
    @Query("SELECT * FROM Blogs")
    fun getAllArticles(): LiveData<List<Blog>>

    @Delete
    suspend fun deleteArticle(blog: Blog)


}