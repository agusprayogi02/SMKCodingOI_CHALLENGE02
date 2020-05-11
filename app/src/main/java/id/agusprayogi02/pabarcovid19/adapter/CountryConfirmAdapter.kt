package id.agusprayogi02.pabarcovid19.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.item.CovidCountryConfirmedItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.country_click_item.*
import java.util.*

class CountryConfirmAdapter(
    private val context: Context,
    private val items: List<CovidCountryConfirmedItem>,
    private val listener: (CovidCountryConfirmedItem) -> Unit
) : RecyclerView.Adapter<CountryConfirmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        context, LayoutInflater.from(context).inflate(
            R.layout.country_click_item, parent, false
        )
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    class ViewHolder(val context: Context, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(
            item: CovidCountryConfirmedItem,
            listener: (CovidCountryConfirmedItem) -> Unit
        ) {
            var ket = ""
            ket = if ((item.provinceState as CharSequence?).isNullOrEmpty()) {
                "Seluruh Wilayah ${item.countryRegion}"
            } else {
                "Kota ${item.provinceState}"
            }
            negara_c.text = item.countryRegion
            negara_bagian_c.text = ket
            konfirmasi_c.text = item.confirmed.toString()
            mati_c.text = item.deaths.toString()
            sembuh_c.text = item.recovered.toString()
            val date =
                java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                    Date(item.lastUpdate)
                )
            date_update_c.text = date
//            pieChart
            val listPie = ArrayList<PieEntry>()
            val listColors = ArrayList<Int>()
            listPie.add(PieEntry(item.active.toFloat(), "Dirawat"))
            listColors.add(ContextCompat.getColor(context, R.color.color_active))
            listPie.add(PieEntry(item.recovered.toFloat(), "Sembuh"))
            listColors.add(ContextCompat.getColor(context, R.color.color_recovered))
            listPie.add(PieEntry(item.deaths.toFloat(), "Meninggal"))
            listColors.add(ContextCompat.getColor(context, R.color.color_death))

            val dataSet = PieDataSet(listPie, "")
            dataSet.colors = listColors
            val pieData = PieData(dataSet)
            pieData.setValueTextSize(14F)
            pie_chart_c.apply {
                data = pieData
                setUsePercentValues(true)
                isDrawHoleEnabled = false
                description.isEnabled = false
                setEntryLabelColor(R.color.onyx)
                animateY(1400, Easing.EaseInOutQuad)
                description.isEnabled = false
                setDrawEntryLabels(false)
            }
            negara_c.setOnClickListener {
                listener(item)
            }
        }
    }

}