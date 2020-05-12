package id.agusprayogi02.pabarcovid19.ui.news

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.session.CountryData
import id.agusprayogi02.pabarcovid19.util.CustomProgressBar
import id.agusprayogi02.pabarcovid19.util.tampilToast
import kotlinx.android.synthetic.main.activity_web_view.*

class WebView : AppCompatActivity() {
    private val progressBar: CustomProgressBar = CustomProgressBar()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = CountryData["urlNews"]
        url_web_view.text = url
        btn_arrow_back.setOnClickListener {
            finish()
        }
        val webView = webview
        webView.settings.loadWithOverviewMode = true
        webView.settings.javaScriptEnabled = true
        webView.isVerticalScrollBarEnabled = true
        var failedLoading = false
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return true
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                failedLoading = true
                if(progressBar.dialog!!.isShowing){
                    progressBar.dialog!!.dismiss()
                }
                tampilToast(this@WebView,"Error: Something went wrong")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (!failedLoading) {
                    progressBar.dialog!!.dismiss()
                    val alertDialog: AlertDialog = AlertDialog.Builder(this@WebView).create()
                    alertDialog.setTitle(url)
                    alertDialog.setMessage("Error: Something went wrong")
                    alertDialog.setButton(
                        AlertDialog.BUTTON_NEUTRAL, "OK"
                    ) { dialog, _ -> dialog.dismiss() }
                    alertDialog.show()
                    finish()
                }else{
                    progressBar.dialog!!.dismiss()
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.show(this@WebView,"Memuat...")
            }
        }
        webView.loadUrl(url)
    }
}
