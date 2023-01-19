package com.silverorange.videoplayer


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.silverorange.videoplayer.network.simpleNetworkCall
import com.silverorange.videoplayer.repo.MainRepository
import com.silverorange.videoplayer.response.VideoPlayerBean
import com.silverorange.videoplayer.utils.coroutine.CoroutineScopedViewModel

class MainViewModel : CoroutineScopedViewModel() {

    private val mutableVideoList = MutableLiveData<List<VideoPlayerBean>>()
    val liveDataVideoList: LiveData<List<VideoPlayerBean>>
        get() = mutableVideoList

    fun getNewsItems() = simpleNetworkCall {
        MainRepository.getVideoList()
    }

    fun setVideoList(item: List<VideoPlayerBean>) {
        val videoData = item
        mutableVideoList.value = item
    }
}