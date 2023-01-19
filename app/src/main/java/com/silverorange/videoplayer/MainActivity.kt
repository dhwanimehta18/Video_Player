package com.silverorange.videoplayer

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.exoplayer2.*
import com.livinglifetechway.k4kotlin.core.isNetworkAvailable
import com.livinglifetechway.k4kotlin.core.orFalse
import com.ravikoradiya.liveadapter.LiveAdapter
import com.silverorange.videoplayer.databinding.ActivityMainBinding
import com.silverorange.videoplayer.databinding.RowVideoDetailBinding
import com.silverorange.videoplayer.network.ifSuccess
import com.silverorange.videoplayer.response.VideoPlayerBean
import com.silverorange.videoplayer.utils.showError
//import io.noties.markwon.html.HtmlPlugin


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

                    item.sortedBy { videoPlayerBean -> videoPlayerBean.publishedAt }
                    val size = item.size
                    for (i in 0..item.size) {
                        item[i].size = size
                    }

                    mViewModel.setVideoList(item)
                    loadInitialUIData()
                } else {
                    showError(mBinding.root, getString(R.string.list_is_empty))
                }
            }
        })
    }

    private fun loadInitialUIData() {
        LiveAdapter(mViewModel.liveDataVideoList, BR.item).map<VideoPlayerBean, RowVideoDetailBinding>(R.layout.row_video_detail) {
            onBind {

                val bind = it.binding
                val item = bind.item


                //HtmlPlugin was not found, therefore converting the html text through ANdroid Html
                /*val markwon = Markwon.builder(this@MainActivity)
                    .usePlugin(HtmlPlugin.create())
                    .build()*/

                var description = ""
                description = if (Build.VERSION.SDK_INT >= 24) {
                    Html.fromHtml(item?.description, Html.FROM_HTML_MODE_LEGACY).toString() // for 24 api and more
                } else {
                    Html.fromHtml(item?.description).toString()// or for older api
                }

                bind.tvDetails.text = description
                bind.tvTitle.text = item?.title
                bind.tvAuthorName.text = item?.author?.name

                val player = ExoPlayer.Builder(this@MainActivity).build()

                // Bind the player to the view.
                bind.exoPlayerVideoContainer.player = player

                // Build the media item
                val mediaItem: MediaItem = MediaItem.fromUri(item?.fullURL.toString())
                // Set the media item to be played.
                player.setMediaItem(mediaItem)
                // Prepare the player.
                player.prepare()
                // Start the playback.
                player.play()


            }
            onClick {

            }
        }.into(mBinding.rvVideoList)
    }

}

