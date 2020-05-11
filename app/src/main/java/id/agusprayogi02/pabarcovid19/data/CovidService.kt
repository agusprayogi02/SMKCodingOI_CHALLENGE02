package id.agusprayogi02.pabarcovid19.data

import retrofit2.Call
import id.agusprayogi02.pabarcovid19.item.CovidConfirmedItem
import retrofit2.http.GET

interface CovidService{
    @GET("api/confirmed")
    fun getConfirmed(): Call<List<CovidConfirmedItem>>
}