package com.example.testapplication.data.local.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    /**
     * Очищаем таблицу перед заполнением
     */
    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE unique_name = :uniqueName")
    suspend fun deleteNews(uniqueName: String)

    @Query("SELECT * from favorite WHERE unique_name = :uniqueName")
    fun getFavoriteNews(uniqueName: String): Flow<Favorite?>

    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllFavorite(): Flow<List<Favorite>>
}