package com.example.skycatnewsapp

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.skycatnewsapp.adapter.SampleStoryAdapter
import com.example.skycatnewsapp.databinding.ActivitySampleStoryBinding
import com.example.skycatnewsapp.viewModel.SampleStoryViewModel

class SampleStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySampleStoryBinding
    private lateinit var viewModel: SampleStoryViewModel
    private lateinit var adapter: SampleStoryAdapter
    private var headline: String = ""
    private var imageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = SampleStoryViewModel(this)

        setAdapter()

        viewObserver()

        headline = intent.getStringExtra("story_headline") ?: ""
        imageUrl = intent.getStringExtra("story_image_url") ?: ""
        val storyId = intent.getStringExtra("story_id") ?: return
        viewModel.fetchStoryById(storyId)

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

    }

    private fun viewObserver() {
        viewModel.sampleStory.observe(this) { story ->
            binding.tvHeadline.text = headline
            Glide.with(this).load(imageUrl).into(binding.ivHeroImage)
            story?.let {
                //Log.d("SampleStoryActivity", "Fetched story: $story") // ✅ log add
                adapter.setStory(it)
            }
        }
    }

    private fun setAdapter() {
        adapter = SampleStoryAdapter()
        binding.rvSimpleStory.adapter = adapter
        binding.rvSimpleStory.layoutManager = LinearLayoutManager(this)
    }
}