package ne.application.com.firebasetest

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_meditation.*
import com.google.android.exoplayer2.util.Util.getUserAgent
import android.os.Build
import com.squareup.picasso.Picasso


class MeditationActivity : AppCompatActivity() {

    private lateinit var player: SimpleExoPlayer

    private lateinit var mainHandler: Handler
    private lateinit var dataSourceFactory: DataSource.Factory
    private lateinit var videoSource: MediaSource
    private lateinit var uri: Uri
    private val bandwidthMeter = DefaultBandwidthMeter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ne.application.com.firebasetest.R.layout.activity_meditation)
        createPlayer()
        attachPlayerView()
        preparePlayer()
        Picasso.get().load(intent.getStringExtra("ImageLink")).into(background);
    }

    fun createPlayer() {
        mainHandler = Handler()
        val renderersFactory = DefaultRenderersFactory(
            this,
            null,
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF
        )
        val trackSelector = DefaultTrackSelector()
        player = ExoPlayerFactory.newSimpleInstance(
            renderersFactory,
            trackSelector
        )
    }

    // Set player to SimpleExoPlayerView
    fun attachPlayerView() {
        simple_view.setPlayer(player)
    }

    // Build Data Source Factory, Dash Media Source, and Prepare player using videoSource
    fun preparePlayer() {
        uriParse()
        dataSourceFactory = buildDataSourceFactory(bandwidthMeter)
        //        videoSource = new DashMediaSource(uri,buildDataSourceFactory(null),new DefaultDashChunkSource.Factory(dataSourceFactory),mainHandler,null);
        videoSource = ExtractorMediaSource(uri, dataSourceFactory, DefaultExtractorsFactory(), mainHandler, null)
        player.prepare(videoSource)
        player.playWhenReady = true
    }

    // Parse VIDEO_URI and Save at uri variable
    fun uriParse() {
        uri = Uri.parse(intent.getStringExtra("link"))
    }

    // Build Data Source Factory using DefaultBandwidthMeter and HttpDataSource.Factory
    private fun buildDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter): DataSource.Factory {
        return DefaultDataSourceFactory(this, bandwidthMeter, buildHttpDataSourceFactory(bandwidthMeter))
    }

    // Build Http Data Source Factory using DefaultBandwidthMeter
    private fun buildHttpDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter): HttpDataSource.Factory {
        val userAgent = Util.getUserAgent(this, "Play Audio")
        return DefaultHttpDataSourceFactory(userAgent, bandwidthMeter)
    }

    // Activity onStop, player must be release because of memory saving
    public override fun onStop() {
        super.onStop()
        player.release()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            simple_view.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            simple_view.onPause()
        }
    }
}
