package com.example.skycatnewsapp.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONArray
import org.json.JSONObject

class LocalJsonInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val responseString = when {
            path.endsWith("news-list") -> {
                context.assets.open("news-list.json").bufferedReader().use { it.readText() }
            }

            path.contains("/story/") -> {
                val requestedId = path.substringAfterLast("/story/")
                val json =
                    context.assets.open("sample_story.json").bufferedReader().use { it.readText() }
                val jsonArray = JSONArray(json)
                var matchedStory: JSONObject? = null

                for (i in 0 until jsonArray.length()) {
                    val storyObj = jsonArray.getJSONObject(i)
                    if (storyObj.getString("id") == requestedId) {
                        matchedStory = storyObj
                        break
                    }
                }

                matchedStory?.toString() ?: "{}"
            }

            else -> "{}"
        }

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(responseString.toByteArray().toResponseBody("application/json".toMediaType()))
            .addHeader("content-type", "application/json")
            .build()
    }
}


//path.contains("/story/") -> {
//    val requestedId = path.substringAfterLast("/story/")
//    val json = context.assets.open("sample_story.json").bufferedReader().use { it.readText() }
//    val jsonObj = JSONObject(json)
//    if (jsonObj.getString("id") == requestedId)
//    { jsonObj.toString() } else { "{}" } }