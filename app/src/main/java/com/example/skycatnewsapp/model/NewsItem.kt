package com.example.skycatnewsapp.model


data class NewsItem(
    val id: String? = null,
    val type: String? = null,
    val headline: String? = null,
    val teaserText: String? = null,
    val creationDate: String? = null,
    val modifiedDate: String? = null,
    val teaserImage: TeaserImage? = null,
    val weblinkUrl: String? = null,
    val url: String? = null
)
