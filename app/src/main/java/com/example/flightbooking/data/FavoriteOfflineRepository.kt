package com.example.flightbooking.data

import kotlinx.coroutines.flow.Flow

class FavoriteOfflineRepository(private val favoriteEntryDao: FavoriteEntryDao): FavoriteRepository {
    override suspend fun insert(favoriteEntry: FavoriteEntry) {
        favoriteEntryDao.insert(favoriteEntry)
    }

    override suspend fun delete(favoriteEntry: FavoriteEntry) {
        favoriteEntryDao.delete(favoriteEntry)
    }

    override fun favorites(): Flow<List<FavoriteEntry>> {
        return favoriteEntryDao.favoritesList()
    }

}