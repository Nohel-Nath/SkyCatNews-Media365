package com.example.skycatnewsapp.model

data class ContentSampleStory(
    val type: String? = null,  // "paragraph" or "image"
    val text: String? = null,
    val url: String? = null,
    val accessibilityText: String? = null
)
