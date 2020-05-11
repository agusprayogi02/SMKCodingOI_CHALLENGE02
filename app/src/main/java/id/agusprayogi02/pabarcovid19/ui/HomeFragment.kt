package id.agusprayogi02.pabarcovid19.ui

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.adapter.CovidConfirmedAdapter
import id.agusprayogi02.pabarcovid19.data.CovidService
import id.agusprayogi02.pabarcovid19.data.apiRequest
import id.agusprayogi02.pabarcovid19.data.httpClient
import id.agusprayogi02.pabarcovid19.item.CovidConfirmedItem
import id.agusprayogi02.pabarcovid19.util.dismissLoading
import id.agusprayogi02.pabarcovid19.util.showLoading
import id.agusprayogi02.pabarcovid19.util.tampilToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApiGetCovidConfirm()
    }

    private fun initImage() {
        val width = DisplayMetrics().widthPixels
        val h = (width / 1.9).toInt()
        Glide.with(this).load("https://covid19.mathdro.id/api/og")
            .apply(RequestOptions().override(width, h))
            .into(image_global_covid)
    }

    private fun callApiGetCovidConfirm() {
        showLoading(context!!, swipe_refresh)

        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient)

        val call = apiRequest.getConfirmed()
        call.enqueue(object : Callback<List<CovidConfirmedItem>> {
            override fun onFailure(call: Call<List<CovidConfirmedItem>>, t: Throwable) {
                dismissLoading(swipe_refresh)
            }

            override fun onResponse(
                call: Call<List<CovidConfirmedItem>>,
                response: Response<List<CovidConfirmedItem>>
            ) {
                dismissLoading(swipe_refresh)

                when {
                    response.isSuccessful -> {
                        when {
                            response.body()?.size != 0 -> {
                                tampilData(response.body()!!)
                                initImage()
                            }
                            else -> {
                                tampilToast(context!!, "Berhasil")
                            }
                        }
                    }
                    else->{
                        tampilToast(context!!,"Gagal")
                    }
                }
            }
        })
    }

    private fun tampilData(body: List<CovidConfirmedItem>) {
        list_country.layoutManager = LinearLayoutManager(context)
        list_country.adapter = CovidConfirmedAdapter(context!!,body){
            tampilToast(context!!,it.countryRegion)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
