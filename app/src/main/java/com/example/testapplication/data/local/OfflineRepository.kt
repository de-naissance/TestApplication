package com.example.testapplication.data.local

import com.example.testapplication.data.local.favorite.Favorite
import com.example.testapplication.data.local.favorite.FavoriteDao
import kotlinx.coroutines.flow.Flow

class OfflineRepository(
    private val favoriteDao: FavoriteDao
): AppRepositoryLocal {
    override fun getAllFavoriteStream(): Flow<List<Favorite>> = favoriteDao.getAllFavorite()

    override fun getFavoriteNews(uniqueName: String): Flow<Favorite?> = favoriteDao.getFavoriteNews(uniqueName)

    override suspend fun insertFavorite(favorite: Favorite) = favoriteDao.insert(favorite)

    override suspend fun deleteFavorite(funiqueName: String) = favoriteDao.deleteNews(funiqueName)
}