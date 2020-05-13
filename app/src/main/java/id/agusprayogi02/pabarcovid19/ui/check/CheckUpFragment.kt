package id.agusprayogi02.pabarcovid19.ui.check

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.util.CustomProgressBar
import id.agusprayogi02.pabarcovid19.util.tampilToast
import kotlinx.android.synthetic.main.fragment_check_up.*

class CheckUpFragment : Fragment() {

    private val URL = "https://checkupcovid19.jatimprov.go.id/covid19/#!/checkup/"
    private val progressBar = CustomProgressBar()

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
        getWebView()
    }

    private fun getWebView() {
        val webView = check_up_web
        webView.apply {
            settings.loadWithOverviewMode = true
            settings.javaScriptEnabled = true
            isVerticalScrollBarEnabled = true
            loadUrl(URL)
        }

        var failedLoading = false
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.show(context!!, "Memuat...")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                    progressBar.dialog!!.dismiss()
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                failedLoading = true
                if (progressBar.dialog!!.isShowing) {
                    progressBar.dialog!!.dismiss()
                }
                tampilToast(context!!, "Error: Something went wrong")
            }
        }
        WebView.setWebContentsDebuggingEnabled(false)
    }

}
