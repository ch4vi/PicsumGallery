package com.ch4vi.picsumgallery.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ch4vi.picsumgallery.data.database.datasource.PictureDao
import com.ch4vi.picsumgallery.data.database.model.PictureCache

@Database(
    entities = [PictureCache::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val NAME = "appDB"
    }

    abstract fun pictureDao(): PictureDao
}
