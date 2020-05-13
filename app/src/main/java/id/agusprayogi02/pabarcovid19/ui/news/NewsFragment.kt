package id.agusprayogi02.pabarcovid19.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.adapter.NewsAdapter
import id.agusprayogi02.pabarcovid19.data.AppConstants
import id.agusprayogi02.pabarcovid19.data.CovidService
import id.agusprayogi02.pabarcovid19.data.apiRequest
import id.agusprayogi02.pabarcovid19.data.httpClient
import id.agusprayogi02.pabarcovid19.item.Article
import id.agusprayogi02.pabarcovid19.item.NewsHealth
import id.agusprayogi02.pabarcovid19.session.CountryData
import id.agusprayogi02.pabarcovid19.util.CustomProgressBar
import id.agusprayogi02.pabarcovid19.util.dismissLoading
import id.agusprayogi02.pabarcovid19.util.showLoading
import id.agusprayogi02.pabarcovid19.util.tampilToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private val progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApinews()
    }

    private fun callApinews() {
        showLoading(context!!, swipe_news)
//        progressBar.show(context!!, "Memuat...")

        val httpClient = httpClient()
        val apiNewsrequest = apiRequest<CovidService>(httpClient, AppConstants.NEWSAPI_URL)

        val call = apiNewsrequest.getNews("id", "health", AppConstants.NEWSAPI_TOKEN)
        call.enqueue(object : Callback<NewsHealth> {

            override fun onFailure(call: Call<NewsHealth>, t: Throwable) {
                tampilToast(context!!, "Gagal " + t.message)
                dismissLoading(swipe_news)
//                progressBar.dialog!!.dismiss()
            }

            override fun onResponse(
                call: Call<NewsHealth>,
                response: Response<NewsHealth>
            ) {
                dismissLoading(swipe_news)
//                progressBar.dialog!!.dismiss()

                when {
                    response.isSuccessful -> {
                        tampilData(response.body()!!.articles)
                    }
                    else -> {
                        tampilToast(context!!, "Gagal")
                    }
                }
            }

        })
    }

    private fun tampilData(list: List<Article>) {
        list_news.layoutManager = LinearLayoutManager(context)
        list_news.adapter = NewsAdapter(context!!, list) {
            CountryData.Session(context)
            CountryData["urlNews"] = it.url
            CountryData["source"] = it.source.name
            val i = Intent(context, WebView::class.java)
            startActivity(i)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
