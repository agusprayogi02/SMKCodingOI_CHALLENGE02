package id.agusprayogi02.pabarcovid19.data

import id.agusprayogi02.pabarcovid19.item.CovidConfirmedItem
import id.agusprayogi02.pabarcovid19.item.CovidCountryConfirmedItem
import id.agusprayogi02.pabarcovid19.item.NewsHealth
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidService {
    @GET("api/confirmed")
    fun getConfirmed(): Call<List<CovidConfirmedItem>>

    @GET("api/countries/{country}/confirmed")
    fun getCountryConfirm(@Path("country") country :String): Call<List<CovidCountryConfirmedItem>>

    @GET("top-headlines?country=id&category=health&apiKey=3e4778c853c140b2a659b0c38882fcd2")
    fun getNews():Call<List<NewsHealth>>
}