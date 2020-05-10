package id.agusprayogi02.pabarcovid19.util

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.agusprayogi02.pabarcovid19.R

fun showLoading(context: Context, swipeRefreshLayout: SwipeRefreshLayout){
    swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimary))
    swipeRefreshLayout.isEnabled = true
    swipeRefreshLayout.isRefreshing = true
}

fun dismissLoading(swipeRefreshLayout: SwipeRefreshLayout){
    swipeRefreshLayout.isEnabled = false
    swipeRefreshLayout.isRefreshing = false
}