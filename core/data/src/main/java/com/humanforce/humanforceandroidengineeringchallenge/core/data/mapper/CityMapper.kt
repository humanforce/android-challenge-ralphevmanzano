package com.humanforce.humanforceandroidengineeringchallenge.core.data.mapper

import com.humanforce.humanforceandroidengineeringchallenge.core.database.entity.CityEntity
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City

object CityMapper {

    fun toDomain(cityEntity: CityEntity): City {
        return City(
            id = cityEntity.id,
            name = cityEntity.name,
            lat = cityEntity.lat,
            long = cityEntity.long,
            country = cityEntity.country,
        )
    }

    fun toEntity(city: City): CityEntity {
        return CityEntity().apply {
            id = city.id
            name = city.name
            lat = city.lat
            long = city.long
            country = city.country
        }
    }
}