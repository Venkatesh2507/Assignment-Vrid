package com.example.assignmentvrid.db

import androidx.room.TypeConverter
import com.example.assignmentvrid.modal.Title

class Converters {

    @TypeConverter
    fun fromTitle(title: Title): String{
        return title.rendered
    }

    @TypeConverter
    fun toTitle(rendered: String): Title {
        return Title(rendered)
    }



}