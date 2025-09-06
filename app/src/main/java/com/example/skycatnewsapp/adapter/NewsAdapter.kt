package com.example.skycatnewsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.skycatnewsapp.R
import com.example.skycatnewsapp.model.NewsItem
import com.example.skycatnewsapp.utility.getRelativeTime
import com.example.skycatnewsapp.utility.setSafeOnClickListener

class NewsAdapter(
    private var newsList: List<NewsItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_TOP_STORY = 0
        private const val VIEW_TYPE_STORY = 1
    }

    override fun getItemViewType(position: Int): Int {
        val item = newsList[position]
        return if (item.id == "1" && item.type == "story") {
            VIEW_TYPE_TOP_STORY
        } else {
            VIEW_TYPE_STORY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TOP_STORY) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_top_story, parent, false)
            TopStoryViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_story, parent, false)
            StoryViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = newsList[position]
        when (holder) {
            is TopStoryViewHolder -> holder.bind(item)
            is StoryViewHolder -> holder.bind(item)
        }
        holder.itemView.setSafeOnClickListener {
            listener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun updateData(newList: List<NewsItem>) {
        newsList = newList.filter { it.type != "advert" }
        notifyDataSetChanged()
    }

    inner class TopStoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgTopStory: ImageView = itemView.findViewById(R.id.imgTopStory)
        private val tvTopHeadline: TextView = itemView.findViewById(R.id.tvTopStoryHeadline)
        private val tvTopTeaser: TextView = itemView.findViewById(R.id.tvTopStoryTeaserText)
        private val tvTopTime: TextView = itemView.findViewById(R.id.tvTopStoryTime)

        fun bind(item: NewsItem) {
            tvTopHeadline.text = item.headline ?: ""
            tvTopTeaser.text = item.teaserText ?: ""
            tvTopTime.text = getRelativeTime(item.creationDate ?: "")

            val imgUrl = item.teaserImage?._links?.url?.href
            val glideModule = GlideUrl(imgUrl, LazyHeaders.Builder().build())
            if (!imgUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(glideModule)
                    .into(imgTopStory)
            }
        }
    }

    inner class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgStory: ImageView = itemView.findViewById(R.id.imgStory)
        private val tvHeadline: TextView = itemView.findViewById(R.id.tvStoryHeadline)
        private val tvTeaser: TextView = itemView.findViewById(R.id.tvStoryTeaser)
        private val tvTime: TextView = itemView.findViewById(R.id.tvStoryTime)
        fun bind(item: NewsItem) {
            tvHeadline.text = item.headline ?: ""
            tvTeaser.text = item.teaserText ?: ""
            tvTime.text = getRelativeTime(item.creationDate ?: "")

            val imgUrl = item.teaserImage?._links?.url?.href
            val glideModule = GlideUrl(imgUrl, LazyHeaders.Builder().build())
            if (!imgUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(glideModule)
                    .into(imgStory)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: NewsItem)
    }
}