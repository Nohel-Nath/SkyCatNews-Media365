package com.example.skycatnewsapp.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skycatnewsapp.api.RetrofitInstance
import com.example.skycatnewsapp.model.SampleStory
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SampleStoryViewModel(private val context: Context) : ViewModel() {

    private val api = RetrofitInstance.getApi(context)

    private val _sampleStory = MutableLiveData<SampleStory>()
    val sampleStory: LiveData<SampleStory> get() = _sampleStory

    fun fetchStoryById(id: String) {
        viewModelScope.launch {
            try {
                val response = api.getSampleStoryDetails(id)
                _sampleStory.postValue(response)
                Log.d("SampleStoryViewModel", "Fetched story id=$id: $response")
            } catch (e: Exception) {
                Log.e("SampleStoryViewModel", "Error fetching story: ${e.message}")
            }
        }
    }
}