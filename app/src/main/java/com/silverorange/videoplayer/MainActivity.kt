package com.silverorange.videoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.livinglifetechway.k4kotlin.core.androidx.isNetworkAvailable
import com.livinglifetechway.k4kotlin.core.isNetworkAvailable
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.orFalse
import com.ravikoradiya.liveadapter.LiveAdapter
import com.silverorange.videoplayer.databinding.ActivityMainBinding
import com.silverorange.videoplayer.databinding.RowVideoDetailBinding
import com.silverorange.videoplayer.network.ifSuccess
import com.silverorange.videoplayer.response.VideoPlayerBean
import com.silverorange.videoplayer.utils.showError

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding
    lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if (isNetworkAvailable().orFalse()) {
            getVideoList()
        } else {
            showError(mBinding.root, R.string.no_network_available)
        }
    }

    private fun getVideoList() {
        mViewModel.getNewsItems().observe(this, Observer {
            it.ifSuccess {
                if (it?.size!! > 0) {
                    val item = it
                    mViewModel.setVideoList(item)
                    loadInitialUIData()
                } else {
                    showError(mBinding.root, getString(R.string.list_is_empty))
                }
            }
        })
    }

    private fun loadInitialUIData() {
        LiveAdapter(mViewModel.liveDataVideoList, BR.item)
            .map<VideoPlayerBean, RowVideoDetailBinding>(R.layout.row_video_detail) {
                onBind {

                    val bind = it.binding
                    val item = bind.item

                    bind.tvDetails.text = item.description
                    bind.tvTitle.text = item.title
                    bind.tvAuthorName.text = item.author?.name

                }
                onClick {

                }
            }
            .into(mBinding.rvVideoList)
    }
}