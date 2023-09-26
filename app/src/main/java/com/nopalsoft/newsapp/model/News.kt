package com.nopalsoft.newsapp.model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content")var content: String?,
    @ColumnInfo(name = "author")var author: String?,
    @ColumnInfo(name = "url")var url: String,
    @ColumnInfo(name = "urlToImage")var urlToImage: String,
    @ColumnInfo(name = "isFavorite")var isFavorite: Boolean = false
)

@Dao
interface NewsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: News)

    @Query("SELECT * FROM news WHERE isFavorite = 1")
    fun getFavoriteNews(): Flow<List<News>>

    @Update
    suspend fun update(news: News)

    @Delete
    suspend fun delete(news: News)
}