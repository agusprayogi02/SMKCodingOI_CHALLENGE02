package id.agusprayogi02.pabarcovid19.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.database.entity.CoronaModel
import id.agusprayogi02.pabarcovid19.session.CountryData
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.country_item.*
import java.util.*

class CovidConfirmedAdapter(
    private val context: Context,
    private val items: List<CoronaModel>,
    private val listener: (CoronaModel) -> Unit
) : RecyclerView.Adapter<CovidConfirmedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        context,
        LayoutInflater.from(context).inflate(R.layout.country_item, parent, false)
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    class ViewHolder(val context: Context, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: CoronaModel, listener: (CoronaModel) -> Unit) {
            Glide.with(context)
                .load("https://www.countryflags.io/" + item.iso2 + "/shiny/64.png")
                .apply { RequestOptions().override(64) }
                .into(flag_country)
            val ket = if (item.provinceState == " ") {
                ""
            } else {
                " (${item.provinceState})"
            }
            negara.text = item.countryRegion + ket

            val dataSort = CountryData["Sorted"]
            when {
                dataSort.equals("Sembuh", true) -> {
                    positif.text = "$dataSort  :  ${item.recovered} Orang"
                    positif.setTextColor(ContextCompat.getColor(context, R.color.color_recovered))
                }
                dataSort.equals("Meninggal", true) -> {
                    positif.text = "$dataSort  :  ${item.deaths} Orang"
                    positif.setTextColor(ContextCompat.getColor(context, R.color.color_death))
                }
                else -> {
                    positif.text = "$dataSort  :  ${item.confirmed} Orang"
                    positif.setTextColor(ContextCompat.getColor(context, R.color.color_confirmed))
                }
            }

            val date = Date(item.lastUpdate)
            last_update.text =
                "Update : " + java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .format(date)
            detail_country.setOnClickListener {
                listener(item)
            }
        }
    }
}