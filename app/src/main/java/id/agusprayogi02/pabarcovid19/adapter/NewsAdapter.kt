package id.agusprayogi02.pabarcovid19.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.database.entity.NewsModel
import id.agusprayogi02.pabarcovid19.item.Article
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.*
import java.util.*

class NewsAdapter(
    private val context: Context,
    private val items: List<NewsModel>,
    private val listener: (NewsModel) -> Unit
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
        fun bindItem(item: NewsModel, listener: (NewsModel) -> Unit) {
            Glide.with(context).load(item.urlToImage).into(image_header)
            news_title.text = item.title
            news_isi.text = item.description
            news_date.text = item.publishedAt
            news_dari.text = item.author
            share_url.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, item.url)
                    type = "text/*"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Bagikan Berita ini")
                it.context.startActivity(shareIntent)
            }
            news_click.setOnClickListener {
                listener(item)
            }
        }
    }
}