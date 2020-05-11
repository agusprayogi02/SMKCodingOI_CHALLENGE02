package id.agusprayogi02.pabarcovid19.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.item.Article
import kotlinx.android.extensions.LayoutContainer

class NewsAdapter(
    private val context: Context,
    private val items: List<Article>,
    private val listener: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(context, LayoutInflater.from(context).inflate(R.layout.news_item, parent, false))

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    class ViewHolder(val context: Context, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: Article, listener: (Article) -> Unit) {

        }
    }
}