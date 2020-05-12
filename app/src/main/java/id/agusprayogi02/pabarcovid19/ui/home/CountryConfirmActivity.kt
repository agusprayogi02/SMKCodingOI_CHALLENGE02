package id.agusprayogi02.pabarcovid19.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.adapter.CountryConfirmAdapter
import id.agusprayogi02.pabarcovid19.data.AppConstants
import id.agusprayogi02.pabarcovid19.data.CovidService
import id.agusprayogi02.pabarcovid19.data.apiRequest
import id.agusprayogi02.pabarcovid19.data.httpClient
import id.agusprayogi02.pabarcovid19.item.CovidCountryConfirmedItem
import id.agusprayogi02.pabarcovid19.session.CountryData
import id.agusprayogi02.pabarcovid19.util.dismissLoading
import id.agusprayogi02.pabarcovid19.util.showLoading
import id.agusprayogi02.pabarcovid19.util.tampilToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_country_confirm.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_confirm)
        apigetData()
    }

    private fun apigetData() {
        showLoading(this, swipe_country)
        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient,AppConstants.COVIDAPI_URL)

        val data = CountryData["country"]
        val call = apiRequest.getCountryConfirm(data!!)
        call.enqueue(object : Callback<List<CovidCountryConfirmedItem>> {
            override fun onFailure(call: Call<List<CovidCountryConfirmedItem>>, t: Throwable) {
                dismissLoading(swipe_country)
            }

            override fun onResponse(
                call: Call<List<CovidCountryConfirmedItem>>,
                response: Response<List<CovidCountryConfirmedItem>>
            ) {
                dismissLoading(swipe_country)

                when {
                    response.isSuccessful -> {
                        when {
                            response.body()?.size != 0 -> {
                                tampilApiData(response.body()!!)
                            }
                            else -> {
                                tampilToast(this@CountryConfirmActivity, "Berhasil")
                            }
                        }
                    }
                    else -> {
                        tampilToast(this@CountryConfirmActivity, "Gagal")
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

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
