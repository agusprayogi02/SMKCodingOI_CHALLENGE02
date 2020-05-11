package id.agusprayogi02.pabarcovid19.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.item.CovidConfirmedItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.country_item.*
import java.util.*

class CovidConfirmedAdapter(
    private val context: Context,
    private val items: List<CovidConfirmedItem>,
    private val listener: (CovidConfirmedItem) -> Unit
) : RecyclerView.Adapter<CovidConfirmedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        context,
        LayoutInflater.from(context).inflate(R.layout.country_item, parent, false)
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position],listener)
    }

    class ViewHolder(val context: Context, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: CovidConfirmedItem, listener: (CovidConfirmedItem) -> Unit) {
            Glide.with(context)
                .load("https://www.countryflags.io/"+item.iso2+"/shiny/64.png")
                .apply{RequestOptions().override(64)}
                .into(flag_country)
            var ket = ""
            if (item.provinceState.isNullOrEmpty()){
                ket = ""
            }else{
                ket = " (${item.provinceState})"
            }
            negara.text = item.countryRegion + ket
            positif.text = "${item.confirmed} Orang"
            last_update.text = Date(item.lastUpdate).toString()
            detail_country.setOnClickListener { listener(item) }
        }
    }
}