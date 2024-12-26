package com.humanforce.humanforceandroidengineeringchallenge.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cities", indices = [Index(value = ["id"], unique = true)])
class CityEntity {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var lat: Double = 0.0
    var long: Double = 0.0
    var country: String = ""
}