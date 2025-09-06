package com.example.skycatnewsapp.model

data class SampleStory(
    val id: String? = null,
    val headline: String? = null,
    val teaserText: String? = null,
    val creationDate: String? = null,
    val modifiedDate: String? = null,
    val teaserImage: TeaserImage? = null,
    val contents: List<ContentSampleStory>? = null
)