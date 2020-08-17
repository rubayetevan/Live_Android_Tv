package live.ebox.tv

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private lateinit var player: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
    }


    override fun onStart() {
        super.onStart()

        val adaptiveTrackSelection: TrackSelection.Factory =
            AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())

        player = ExoPlayerFactory.newSimpleInstance(
            this,
            DefaultRenderersFactory(this),
            DefaultTrackSelector(adaptiveTrackSelection),
            DefaultLoadControl()
        )

        video_view.player = player

        makePlayerFullScreen()

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            this, Util.getUserAgent(this, "Exo2"), DefaultBandwidthMeter.Builder(this).build()
        )

        val bundle = intent.extras
        val url = bundle!!.getString("Url")

        val uri: Uri = Uri.parse(url)
        val contentType = Util.inferContentType(uri)

        val mediaSource = if (contentType == C.TYPE_HLS) {
            HlsMediaSource.Factory(dataSourceFactory)
                .setAllowChunklessPreparation(true)
                .createMediaSource(uri)
        } else {
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        }

        player.prepare(mediaSource)

        player.playWhenReady = playWhenReady

        player.addListener(object : Player.EventListener {

            override fun onTimelineChanged(
                timeline: Timeline,
                manifest: Any?,
                reason: Int
            ) {
            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray,
                trackSelections: TrackSelectionArray
            ) {
            }

            override fun onLoadingChanged(isLoading: Boolean) {}

            override fun onPlayerStateChanged(
                playWhenReady: Boolean,
                playbackState: Int
            ) {
                when (playbackState) {
                    ExoPlayer.STATE_READY -> loading.visibility = View.GONE
                    ExoPlayer.STATE_BUFFERING -> loading.visibility = View.VISIBLE
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {}
            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
            override fun onPlayerError(error: ExoPlaybackException) {}
            override fun onPositionDiscontinuity(reason: Int) {}
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
            override fun onSeekProcessed() {}
        })

        player.seekTo(currentWindow, playbackPosition)
        player.prepare(mediaSource, true, false)
    }

    private fun makePlayerFullScreen() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val params = video_view?.layoutParams
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.MATCH_PARENT
        video_view?.layoutParams = params
    }

    private fun releasePlayer() {
        if (this::player.isInitialized) {
            playbackPosition = player.currentPosition
            currentWindow = player.currentWindowIndex
            playWhenReady = player.playWhenReady
            player.release()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }
}