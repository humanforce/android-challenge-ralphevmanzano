package com.humanforce.humanforceandroidengineeringchallenge.core.database.di

import android.app.Application
import androidx.room.Room
import com.humanforce.humanforceandroidengineeringchallenge.core.database.CityDao
import com.humanforce.humanforceandroidengineeringchallenge.core.database.CityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CityDatabase {
        return Room.databaseBuilder(app, CityDatabase::class.java, "cities_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCityDao(database: CityDatabase): CityDao {
        return database.cityDao()
    }
}