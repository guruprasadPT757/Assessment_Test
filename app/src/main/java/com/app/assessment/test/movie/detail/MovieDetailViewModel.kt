package com.app.assessment.test.movie.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.assessment.test.BuildConfig
import com.app.assessment.test.RetrofitBuilder
import com.app.assessment.test.models.video.VideoItem
import kotlinx.coroutines.launch

class MovieDetailViewModel: ViewModel() {
    val videoLinkLiveData = MutableLiveData<VideoItem>()
    val errorLiveData = MutableLiveData<String>()

    fun getVideoInformation(videoId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitBuilder.moviesApiService.getVideInformation(videoId, BuildConfig.API_KEY)
                if (response.id == videoId && response.results?.isNotEmpty() == true) {
                    videoLinkLiveData.postValue(response.results.first())
                }
            } catch (exp: Exception) {
                errorLiveData.postValue(exp.message)
            }
        }
    }
}