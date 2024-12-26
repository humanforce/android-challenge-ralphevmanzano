package com.humanforce.humanforceandroidengineeringchallenge.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.humanforce.humanforceandroidengineeringchallenge.core.database.entity.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = true)
abstract class CityDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}