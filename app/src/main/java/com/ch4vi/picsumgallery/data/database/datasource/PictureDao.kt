package com.ch4vi.picsumgallery.data.database.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ch4vi.picsumgallery.data.database.model.PictureCache
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {

    @Transaction
    @Query("SELECT * FROM picture_cache")
    fun getAllPictures(): Flow<List<PictureCache>?>

    @Query("SELECT * FROM picture_cache WHERE author = :author")
    fun getPicturesByAuthor(author: String): Flow<List<PictureCache>?>

    @Query("SELECT DISTINCT author FROM picture_cache")
    fun getAuthors(): Flow<List<String>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(page: List<PictureCache>)

    @Query("DELETE FROM picture_cache")
    suspend fun clearAll()
}
