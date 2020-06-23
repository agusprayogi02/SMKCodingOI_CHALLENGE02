package id.agusprayogi02.pabarcovid19.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "periksa")
data class PeriksaModel(@PrimaryKey var Key: String, var Nilai: String, var Date: String) {
    constructor() : this("", "", "")
}