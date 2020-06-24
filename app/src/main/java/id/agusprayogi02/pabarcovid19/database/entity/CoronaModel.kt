package id.agusprayogi02.pabarcovid19.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "corona")
data class CoronaModel(
    val active: Int,
    val admin2: String,
    val combinedKey: String,
    val confirmed: Int,
    val countryRegion: String,
    val deaths: Int,
    val fips: String,
    val incidentRate: Double,
    val iso2: String,
    val iso3: String,
    val lastUpdate: Long,
    val lat: Double,
    val long: Double,
    val peopleHospitalized: String,
    val peopleTested: String,
    val provinceState: String,
    val recovered: Int,
    @PrimaryKey val uid: Int
) {
    constructor() : this(0, "", "", 0, "", 0, "", 0.0, "", "", 0, 0.0, 0.0, "", "", "", 0, 0)
}
