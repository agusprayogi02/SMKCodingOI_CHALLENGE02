package id.agusprayogi02.pabarcovid19.item


import com.google.gson.annotations.SerializedName

data class CovidCountryConfirmedItem(
    @SerializedName("provinceState")
    val provinceState: Any,
    @SerializedName("countryRegion")
    val countryRegion: String,
    @SerializedName("lastUpdate")
    val lastUpdate: Long,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("long")
    val long: Double,
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("recovered")
    val recovered: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("active")
    val active: Int,
    @SerializedName("admin2")
    val admin2: Any,
    @SerializedName("fips")
    val fips: Any,
    @SerializedName("combinedKey")
    val combinedKey: String,
    @SerializedName("incidentRate")
    val incidentRate: Double,
    @SerializedName("peopleTested")
    val peopleTested: Any,
    @SerializedName("peopleHospitalized")
    val peopleHospitalized: Any,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("iso3")
    val iso3: String
)