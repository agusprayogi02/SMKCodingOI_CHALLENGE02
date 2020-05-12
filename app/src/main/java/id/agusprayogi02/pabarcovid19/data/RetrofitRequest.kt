package id.agusprayogi02.pabarcovid19.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun httpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val builder = OkHttpClient.Builder()
    builder.readTimeout(15, TimeUnit.SECONDS)
    builder.connectTimeout(15, TimeUnit.SECONDS)
    builder.addInterceptor(logging)

    return builder.build()
}

inline fun <reified T> apiRequest(okHttpClient: OkHttpClient,base_url:String): T {
    val gson = GsonBuilder().create()

    val retrofit = Retrofit.Builder()
        .baseUrl(base_url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit.create(T::class.java)
}