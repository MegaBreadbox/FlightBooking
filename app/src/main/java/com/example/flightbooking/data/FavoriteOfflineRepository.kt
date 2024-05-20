package com.example.flightbooking.data

import kotlinx.coroutines.flow.Flow

class FavoriteOfflineRepository(private val favoriteEntryDao: FavoriteEntryDao): FavoriteRepository {
    override suspend fun insert(favorite: favorite) {
        favoriteEntryDao.insert(favorite)
    }

    override suspend fun delete(favorite: favorite) {
        favoriteEntryDao.delete(favorite)
    }

    override fun favorites(): Flow<List<favorite>> {
        return favoriteEntryDao.favoritesList()
    }

}