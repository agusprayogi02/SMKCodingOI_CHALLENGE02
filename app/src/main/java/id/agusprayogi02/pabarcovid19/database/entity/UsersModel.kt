package id.agusprayogi02.pabarcovid19.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersModel(
    @PrimaryKey var uid: String,
    var name: String,
    var email: String,
    var password: String,
    var phone: String,
    var umur: String,
    var Jk: String,
    var alamat: String,
    val foto: String
) {
    constructor() : this("", "", "", "", "", "", "", "", "")
}