package com.example.skycatnewsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skycatnewsapp.adapter.NewsAdapter
import com.example.skycatnewsapp.databinding.ActivityMainBinding
import com.example.skycatnewsapp.model.NewsItem
import com.example.skycatnewsapp.viewModel.NewsViewModel

class MainActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = NewsViewModel(this)

        setAdapter()

        viewObserver()

        viewModel.fetchNews()
    }

    private fun setAdapter() {
        adapter = NewsAdapter(emptyList(), this)
        binding.rvSkyCatNews.adapter = adapter
        binding.rvSkyCatNews.layoutManager = LinearLayoutManager(this)
    }

    private fun viewObserver() {
        viewModel.newsList.observe(this, Observer { news ->
            if (news != null) {
                binding.tvHeadline.text = news.title
                news.data?.let { adapter.updateData(it) }
                //Log.d("MainActivity", "Articles: ${news.data}")
            } else {
                Log.d("MainActivity", "News is null")
            }
        })
    }

    override fun onItemClick(item: NewsItem) {
        if (item.type == "story") {
            val intent = Intent(this, SampleStoryActivity::class.java)
            intent.putExtra("story_id", item.id)
            intent.putExtra("story_headline", item.headline)
            intent.putExtra("story_image_url", item.teaserImage?._links?.url?.href)
            startActivity(intent)
        } else if (item.type == "weblink") {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.weblinkUrl))
            startActivity(browserIntent)
        }
    }
}