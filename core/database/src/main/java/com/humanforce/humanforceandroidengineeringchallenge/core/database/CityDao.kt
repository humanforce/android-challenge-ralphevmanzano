package com.humanforce.humanforceandroidengineeringchallenge.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.humanforce.humanforceandroidengineeringchallenge.core.database.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("SELECT * FROM cities")
    fun loadFavoriteCities(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCity(city: CityEntity)

    @Query("DELETE FROM cities WHERE id = :id")
    suspend fun removeFavoriteCity(id: Int)
}