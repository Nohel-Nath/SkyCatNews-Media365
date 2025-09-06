package com.example.skycatnewsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skycatnewsapp.R
import com.example.skycatnewsapp.model.ContentSampleStory
import com.example.skycatnewsapp.model.SampleStory

class SampleStoryAdapter(private var story: SampleStory? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_PARAGRAPH = 0
        private const val TYPE_IMAGE = 1
    }

    override fun getItemViewType(position: Int): Int {
        val content = story?.contents?.get(position)
        return when (content?.type) {
            "paragraph" -> TYPE_PARAGRAPH
            "image" -> TYPE_IMAGE
            else -> TYPE_PARAGRAPH
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_PARAGRAPH) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sample_story_paragraph, parent, false)
            ParagraphViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sample_story_image, parent, false)
            ImageViewHolder(view)
        }
    }

    override fun getItemCount(): Int = story?.contents?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = story?.contents?.get(position) ?: return
        when (holder) {
            is ParagraphViewHolder -> holder.bind(content)
            is ImageViewHolder -> holder.bind(content)
        }
    }

    fun setStory(newStory: SampleStory) {
        story = newStory
        notifyDataSetChanged()
    }

    inner class ParagraphViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvParagraph: TextView = itemView.findViewById(R.id.tvParagraph)
        fun bind(content: ContentSampleStory) {
            tvParagraph.text = content.text ?: ""
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivContentImage: ImageView = itemView.findViewById(R.id.ivContentImage)
        fun bind(content: ContentSampleStory) {
            content.url?.let {
                if (it.isNotEmpty()) {
                    Glide.with(itemView.context).load(it).into(ivContentImage)
                }
            }
        }
    }

}