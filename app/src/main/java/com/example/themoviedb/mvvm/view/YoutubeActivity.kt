package com.example.themoviedb.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.themoviedb.R
import com.example.themoviedb.utils.App
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_youtube.*


class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val RECOVERY_REQUEST = 1
    private var youTubeView: YouTubePlayerView? = null
    private var idYt = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        youTubeView = youtube_view as YouTubePlayerView
        youTubeView!!.initialize(App.YT_API_KEY, this)

        idYt = intent.getStringExtra("idYt").toString()
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (!p2) {
            p1?.loadVideo(idYt)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        errorReason: YouTubeInitializationResult?
    ) {
        if (errorReason?.isUserRecoverableError!!) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show()
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(App.YT_API_KEY, this)
        }
    }

    protected fun getYouTubePlayerProvider(): YouTubePlayer.Provider {
        return youTubeView!!
    }
}
