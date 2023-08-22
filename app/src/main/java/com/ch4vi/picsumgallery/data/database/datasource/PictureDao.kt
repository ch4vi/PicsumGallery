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
    @Query("SELECT * FROM picture_cache LIMIT :limit OFFSET :offset")
    fun getAllPictures(limit: Int, offset: Int): Flow<List<PictureCache>?>

    @Query("SELECT * FROM picture_cache WHERE author = :author")
    fun getPicturesByAuthor(author: String): Flow<List<PictureCache>?>

    @Query("SELECT * FROM picture_cache ORDER BY width ASC")
    fun getPicturesByWidthAscending(): Flow<List<PictureCache>?>

    @Query("SELECT * FROM picture_cache ORDER BY width DESC")
    fun getPicturesByWidthDescending(): Flow<List<PictureCache>?>

    @Query("SELECT * FROM picture_cache ORDER BY height ASC")
    fun getPicturesByHeightAscending(): Flow<List<PictureCache>?>

    @Query("SELECT * FROM picture_cache ORDER BY height DESC")
    fun getPicturesByHeightDescending(): Flow<List<PictureCache>?>

    @Query("SELECT author FROM picture_cache")
    fun getAuthors(): Flow<List<String>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(page: List<PictureCache>)

    @Query("DELETE FROM picture_cache")
    suspend fun clearAll()
}
