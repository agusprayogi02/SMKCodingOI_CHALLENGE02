package id.agusprayogi02.pabarcovid19.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "corona")
data class CoronaModel(
    @PrimaryKey var uid: Int,
    var iso2: String,
    var provinceState: String,
    var countryRegion: String,
    var recovered: Int,
    var deaths: Int,
    var confirmed: Int,
    var lastUpdate: Long
) {
    constructor() : this(0, "", "", "", 0, 0, 0, 0)
}