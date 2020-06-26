package id.agusprayogi02.pabarcovid19.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.adapter.NewsAdapter
import id.agusprayogi02.pabarcovid19.data.AppConstants
import id.agusprayogi02.pabarcovid19.data.CovidService
import id.agusprayogi02.pabarcovid19.data.apiRequest
import id.agusprayogi02.pabarcovid19.data.httpClient
import id.agusprayogi02.pabarcovid19.database.entity.NewsModel
import id.agusprayogi02.pabarcovid19.item.NewsHealth
import id.agusprayogi02.pabarcovid19.session.CountryData
import id.agusprayogi02.pabarcovid19.util.*
import id.agusprayogi02.pabarcovid19.viewmodel.NewsViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private val progressBar = CustomProgressBar()
    lateinit var news: ArrayList<NewsModel>
    private val viewModel by viewModels<NewsViewModel>()

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
        viewModel.init(requireContext())
        callApinews()
        tampilData()
    }

    private fun callApinews() {
        showLoading(requireContext(), swipe_news)
//        progressBar.show(context!!, "Memuat...")

        val httpClient = httpClient()
        val apiNewsrequest = apiRequest<CovidService>(httpClient, AppConstants.NEWSAPI_URL)

        val call = apiNewsrequest.getNews("id", "health", AppConstants.NEWSAPI_TOKEN)
        call.enqueue(object : Callback<NewsHealth> {

            override fun onFailure(call: Call<NewsHealth>, t: Throwable) {
                tampilToast(context!!, "Gagal " + t.message)
                if (swipe_news != null) {
                    dismissLoading(swipe_news)
                }
//                progressBar.dialog!!.dismiss()
            }

            override fun onResponse(
                call: Call<NewsHealth>,
                response: Response<NewsHealth>
            ) {
                if (swipe_news != null) {
                    dismissLoading(swipe_news)
                }
//                progressBar.dialog!!.dismiss()

                when {
                    response.isSuccessful -> {
//                        tampilData(response.body()!!.articles)
                        response.body()!!.articles.let { list ->
                            news = ArrayList()
                            var i = 1

                            for (new in list) {
                                val data = NewsModel(
                                    i,
                                    new.source.name,
                                    new.title,
                                    new.description,
                                    new.url,
                                    new.urlToImage,
                                    new.publishedAt
                                )
                                i++
                                news.add(data)
                            }
                            viewModel.insertAll(news)
                        }
                    }
                    else -> {
                        connnetError(activity!!)
                    }
                }
            }

        })
    }

    private fun tampilData() {
        list_news.layoutManager = LinearLayoutManager(context)
        viewModel.newsAll.observe(viewLifecycleOwner, Observer { list ->
            list.let {
                list_news.adapter = NewsAdapter(requireContext(), it) { data ->
                    CountryData.Session(context)
                    CountryData["urlNews"] = data.url
                    CountryData["source"] = data.author
                    val i = Intent(context, WebView::class.java)
                    startActivity(i)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
