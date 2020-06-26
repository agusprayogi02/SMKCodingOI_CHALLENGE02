package id.agusprayogi02.pabarcovid19.ui.check

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.thecode.aestheticdialogs.AestheticDialog
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.adapter.PeriksaAdapter
import id.agusprayogi02.pabarcovid19.database.entity.PeriksaModel
import id.agusprayogi02.pabarcovid19.session.SessionData
import id.agusprayogi02.pabarcovid19.util.dismissLoading
import id.agusprayogi02.pabarcovid19.util.showLoading
import id.agusprayogi02.pabarcovid19.util.tampilToast
import id.agusprayogi02.pabarcovid19.viewmodel.PeriksaViewModel
import kotlinx.android.synthetic.main.fragment_check_up.*

class CheckUpFragment : Fragment() {

//    private val URL = "https://checkupcovid19.jatimprov.go.id/covid19/#!/checkup/"
//    private val progressBar = CustomProgressBar()

    lateinit var ref: DatabaseReference
    var dataPeriksa: ArrayList<PeriksaModel> = ArrayList()
    private val viewModel by viewModels<PeriksaViewModel>()
    private var adapter: PeriksaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getWebView()
        getData()
        viewModel.init(requireContext())
        viewModel.allPeriksa.observe(viewLifecycleOwner, Observer { periksa ->
            init(periksa)
            periksa.map {
                val data = it
                hasil_test.text = it.Kondisi
                val nilai = data.Nilai.toInt()
                val jawab: String = if (nilai == 0) {
                    getString(R.string.jawab_1)
                } else if (nilai == 1) {
                    getString(R.string.jawab_2)
                } else if (nilai in 2..4) {
                    getString(R.string.jawab_3)
                } else {
                    getString(R.string.jawab_4)
                }
                kesimpulan.text = jawab
                val color = if (nilai == 0) {
                    resources.getColor(R.color.color_recovered)
                } else if (nilai == 1) {
                    resources.getColor(R.color.md_light_green_300)
                } else if (nilai in 2..4) {
                    resources.getColor(R.color.color_active)
                } else {
                    resources.getColor(R.color.color_death)
                }
                kesimpulan.setTextColor(color)
                hasil_test.setTextColor(color)
                btn_check_up.text = "Check Up Again"
                btn_check_up.setPadding(10, 0, 10, 0)
            }

        })
        btn_check_up.setOnClickListener {
            val i = Intent(activity, CheckUpActivity::class.java)
            startActivity(i)
        }
    }

    private fun getData() {
        showLoading(requireContext(), swipe_check)
        val uid = SessionData["UserData"]
        ref = FirebaseDatabase.getInstance().reference
        ref.child(uid!!).child("History").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                tampilToast(requireContext(), "Database Error!")
                dismissLoading(swipe_check)
            }

            override fun onDataChange(p0: DataSnapshot) {
                dataPeriksa = ArrayList()
                for (h in p0.children) {
                    val periksa = h.getValue(PeriksaModel::class.java)
                    periksa?.Key = (h.key!!)
                    dataPeriksa.add(periksa!!)
                }
                viewModel.insertAll(dataPeriksa)
                if (swipe_check != null) {
                    dismissLoading(swipe_check)
                }
            }

        })
    }

    private fun init(data: List<PeriksaModel>) {
        list_check_up.layoutManager = LinearLayoutManager(context)
        adapter = PeriksaAdapter(requireContext(), data) { periksa ->
            ref.child(SessionData["UserData"]!!).child("History").child(periksa.Key).removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        AestheticDialog.showToaster(
                            activity,
                            "Hapus",
                            "Data Berhasil diHapus",
                            AestheticDialog.SUCCESS
                        )
                        viewModel.delete(periksa)
                    }
                }
        }
        list_check_up.adapter = adapter
    }

//    private fun getWebView() {
//        val webView = check_up_web
//        webView.apply {
//            settings.loadWithOverviewMode = true
//            settings.javaScriptEnabled = true
//            isVerticalScrollBarEnabled = true
//            loadUrl(URL)
//        }
//
//        var failedLoading = false
//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(
//                view: WebView?,
//                request: WebResourceRequest?
//            ): Boolean {
//                return true
//            }
//
//            override fun onReceivedError(
//                view: WebView?,
//                request: WebResourceRequest?,
//                error: WebResourceError?
//            ) {
//                failedLoading = true
//                if (progressBar.dialog!!.isShowing) {
//                    progressBar.dialog!!.dismiss()
//                }
//                tampilToast(context!!, "Error: Something went wrong")
//            }
//        }
//    }

}
