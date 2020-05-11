package id.agusprayogi02.pabarcovid19.item


import com.google.gson.annotations.SerializedName

data class NewsHealth(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Article>
)