package id.agusprayogi02.pabarcovid19.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.database.entity.PeriksaModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.periksa_item.*

class PeriksaAdapter(
    private val context: Context,
    private val items: List<PeriksaModel>,
    private val listener: (PeriksaModel) -> Unit
) : RecyclerView.Adapter<PeriksaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        context,
        LayoutInflater.from(context).inflate(R.layout.periksa_item, parent, false)
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }


    class ViewHolder(val context: Context, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: PeriksaModel, listener: (PeriksaModel) -> Unit) {
            date_periksa.text = item.Date
            hasil_periksa.text = item.Kondisi
            btn_hps_periksa.setOnClickListener {
                listener(item)
            }
        }
    }

}