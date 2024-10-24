package com.example.assignmentvrid.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Blogs")
data class Blog(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    @SerializedName("date")
    var date: String,
    @SerializedName("title")
    var title: Title,
    @SerializedName("jetpack_featured_media_url")
    var jetpackFeaturedMediaUrl: String,
    @SerializedName("link")
    var link: String


):Serializable
