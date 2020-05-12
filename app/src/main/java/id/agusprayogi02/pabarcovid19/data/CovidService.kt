package id.agusprayogi02.pabarcovid19.data

import id.agusprayogi02.pabarcovid19.item.CovidConfirmedItem
import id.agusprayogi02.pabarcovid19.item.CovidCountryConfirmedItem
import id.agusprayogi02.pabarcovid19.item.NewsHealth
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CovidService {
    @GET("api/confirmed")
    fun getConfirmed(): Call<List<CovidConfirmedItem>>

    @GET("api/countries/{country}/confirmed")
    fun getCountryConfirm(@Path("country") country: String): Call<List<CovidCountryConfirmedItem>>

    @GET("v2/top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsHealth>
}