package com.example.skycatnewsapp.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skycatnewsapp.model.SkyCateNews
import androidx.lifecycle.viewModelScope
import com.example.skycatnewsapp.api.RetrofitInstance
import kotlinx.coroutines.launch

class NewsViewModel(private val context: Context) : ViewModel() {
    private val _newsList = MutableLiveData<SkyCateNews>()
    val newsList: LiveData<SkyCateNews> get() = _newsList

    private val api = RetrofitInstance.getApi(context)

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val response = api.getNewsList()
                _newsList.postValue(response)
                Log.d("NewsViewModel", "Articles: ${response.data}")
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Error fetching news: ${e.message}")
            }
        }
    }
}