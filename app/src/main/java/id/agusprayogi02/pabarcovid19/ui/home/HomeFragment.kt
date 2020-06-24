package id.agusprayogi02.pabarcovid19.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.adapter.CovidConfirmedAdapter
import id.agusprayogi02.pabarcovid19.data.AppConstants
import id.agusprayogi02.pabarcovid19.data.CovidService
import id.agusprayogi02.pabarcovid19.data.apiRequest
import id.agusprayogi02.pabarcovid19.data.httpClient
import id.agusprayogi02.pabarcovid19.item.CovidConfirmedItem
import id.agusprayogi02.pabarcovid19.session.CountryData
import id.agusprayogi02.pabarcovid19.util.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var dataSort = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CountryData.Session(context)
        CountryData["Sorted"] = "Terkonfirmasi"
        callApiGetCovidConfirm()
        setSpinner()
        setSorted()
    }

    private fun setSorted() {
        btn_set.setOnClickListener {
            val isi = spin_sort.selectedItem.toString()
            CountryData["Sorted"] = isi
            dataSort = isi
            callApiGetCovidConfirm()
        }
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_string,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_sort.adapter = adapter
    }

    private fun initImage() {
        val width = DisplayMetrics().widthPixels
        val h = (width / 1.9).toInt()
        Glide.with(this).load("https://covid19.mathdro.id/api/og")
            .apply(RequestOptions().override(width, h))
            .into(image_global_covid)
    }

    private fun callApiGetCovidConfirm() {
        showLoading(requireContext(), swipe_refresh)
//        progressBar.show(context!!, "Memuat..")

        val httpClient = httpClient()
        val apiRequest = apiRequest<CovidService>(httpClient, AppConstants.COVIDAPI_URL)
        val call = when {
            dataSort.equals("Sembuh",true) -> {
                apiRequest.getRecovered()
            }
            dataSort.equals("Meninggal",true) -> {
                apiRequest.getDeaths()
            }
            else -> {
                apiRequest.getConfirmed()
            }
        }

        call.enqueue(object : Callback<List<CovidConfirmedItem>> {
            override fun onFailure(call: Call<List<CovidConfirmedItem>>, t: Throwable) {
                tampilToast(context!!, "Gagal " + t.message)
                dismissLoading(swipe_refresh)
//                progressBar.dialog!!.dismiss()
            }

            override fun onResponse(
                call: Call<List<CovidConfirmedItem>>,
                response: Response<List<CovidConfirmedItem>>
            ) {
                dismissLoading(swipe_refresh)
//                progressBar.dialog!!.dismiss()

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
                    else -> {
                        connnetError(activity!!)
                    }
                }
            }
        })
    }

    private fun tampilData(body: List<CovidConfirmedItem>) {
        list_country.layoutManager = LinearLayoutManager(context)
        list_country.adapter = CovidConfirmedAdapter(requireContext(), body) {
            CountryData.Session(context)
            CountryData["country"] = it.countryRegion
            val intent = Intent(context, CountryConfirmActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
