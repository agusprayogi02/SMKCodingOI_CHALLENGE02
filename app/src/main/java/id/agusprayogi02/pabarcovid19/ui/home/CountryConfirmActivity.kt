package id.agusprayogi02.pabarcovid19.ui.home

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.adapter.CountryConfirmAdapter
import id.agusprayogi02.pabarcovid19.data.AppConstants
import id.agusprayogi02.pabarcovid19.data.CovidService
import id.agusprayogi02.pabarcovid19.data.apiRequest
import id.agusprayogi02.pabarcovid19.data.httpClient
import id.agusprayogi02.pabarcovid19.item.CovidCountryConfirmedItem
import id.agusprayogi02.pabarcovid19.session.CountryData
import id.agusprayogi02.pabarcovid19.util.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_country_confirm.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryConfirmActivity : AppCompatActivity() {

    private val progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_confirm)
        apigetData()
    }

    private fun apigetData() {
        showLoading(this, swipe_country)
        progressBar.show(this, "Memuat...")

        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVIDAPI_URL)

        val data = CountryData["country"]
        val call = apiRequest.getCountryConfirm(data!!)
        call.enqueue(object : Callback<List<CovidCountryConfirmedItem>> {
            override fun onFailure(call: Call<List<CovidCountryConfirmedItem>>, t: Throwable) {
                tampilToast(this@CountryConfirmActivity, "Gagal " + t.message)
                dismissLoading(swipe_country)
                progressBar.dialog!!.dismiss()
            }

            override fun onResponse(
                call: Call<List<CovidCountryConfirmedItem>>,
                response: Response<List<CovidCountryConfirmedItem>>
            ) {
                dismissLoading(swipe_country)
                progressBar.dialog!!.dismiss()

                when {
                    response.isSuccessful -> {
                        when {
                            response.body()?.size != 0 -> {
                                tampilApiData(response.body()!!)
                                initImage()
                            }
                            else -> {
                                tampilToast(this@CountryConfirmActivity, "Berhasil")
                            }
                        }
                    }
                    else -> {
                        connnetError(this@CountryConfirmActivity)
                    }
                }
            }

        })
    }


    private fun tampilApiData(list: List<CovidCountryConfirmedItem>) {
        list_country_clicked.layoutManager = LinearLayoutManager(this)
        list_country_clicked.adapter = CountryConfirmAdapter(this, list) {
            tampilToast(this, it.countryRegion)
        }
    }

    private fun initImage() {
        val width = DisplayMetrics().widthPixels
        val h = (width / 1.9).toInt()
        Glide.with(this)
            .load(AppConstants.COVIDAPI_URL + "api/countries/${CountryData["country"]}/og")
            .apply(RequestOptions().override(width, h))
            .into(image_country_covid)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
