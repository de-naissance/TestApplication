package com.example.testapplication.data.local

import com.example.testapplication.data.local.favorite.Favorite
import kotlinx.coroutines.flow.Flow

interface AppRepositoryLocal {

    /**
     * Извлеките все элементы из данного источника данных.
     */
    fun getAllFavoriteStream(): Flow<List<Favorite>>

    /**
     * Извлеките элемент из заданного источника данных, который соответствует id.
     */
    fun getFavoriteNews(uniqueName: String): Flow<Favorite?>

    /**
     * Вставить элемент в источник данных
     */
    suspend fun insertFavorite(favorite: Favorite)
    /**
     * Удалить элемент из источника данных
     */
    suspend fun deleteFavorite(uniqueName: String)
}