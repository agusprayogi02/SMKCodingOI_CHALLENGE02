package id.agusprayogi02.pabarcovid19.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsModel(
    @PrimaryKey() var uid: Int,
    var author: String,
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String
) {
    constructor() : this(0, "", "", "", "", "", "")
}