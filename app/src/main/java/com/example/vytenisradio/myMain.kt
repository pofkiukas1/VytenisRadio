package com.example.vytenisradio
import android.app.Activity
import android.media.*
import android.os.Bundle
import android.media.MediaPlayer.OnBufferingUpdateListener
import android.media.MediaPlayer.OnPreparedListener
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import java.io.IOException
import com.example.vytenisradio.R
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class myMain : Activity(), View.OnClickListener {

    private var buttonPlay: Button? = null
    private var buttonStop: Button? = null


    private var player: MediaPlayer? = null

    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUIElements()
        initializeMediaPlayer()
    }
    fun createTempFile(): java.io.File {
        return java.io.File.createTempFile("audio", ".tmp")
    }
    private fun initializeUIElements() {

        buttonPlay = findViewById<View>(R.id.button) as Button
        buttonPlay!!.setOnClickListener(this)

        buttonStop = findViewById<View>(R.id.stopButton) as Button
        buttonStop!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        println("click")
        if (v === buttonPlay) {
//            try {
//                val url = "http://78.58.52.217:8000" // your URL here
//
//                val connection = URL(url).openConnection() as HttpURLConnection
//                connection.connectTimeout = 10000 // 10 seconds
//
//                val inputStream = BufferedInputStream(connection.inputStream)
//                val tempFile = createTempFile()
//                val outputStream = FileOutputStream(tempFile)
//
//                inputStream.copyTo(outputStream)
//
//                inputStream.close()
//                outputStream.close()
//
//                val extractor = MediaExtractor()
//                extractor.setDataSource(tempFile.absolutePath)
//
//                val trackCount = extractor.trackCount
//                for (i in 0 until trackCount) {
//                    val format = extractor.getTrackFormat(i)
//                    val mime = format.getString(MediaFormat.KEY_MIME)
//                    if (mime!!.startsWith("audio/")) {
//                        extractor.selectTrack(i)
//                        val codec = MediaCodec.createDecoderByType(mime)
//                        codec.configure(format, null, null, 0)
//                        codec.start()
//                        // Handle input and output buffers
//                        break
//                    }
//                }
//
//                extractor.release()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }



            startPlaying()
        }
        if (v === buttonStop) {
            stopPlaying()
        }
    }

    private fun startPlaying() {

        buttonPlay!!.isEnabled = false
        player!!.setOnErrorListener { mp, what, extra ->
            Log.e("MediaPlayerError", "Error occurred: $what, $extra")
            // Handle the error here
            false // Return false to indicate that you have handled the error
        }
        player!!.prepareAsync()

        player!!.setOnPreparedListener { player!!.start() }

    }

    private fun stopPlaying() {
        if (player!!.isPlaying) {
            player!!.stop()
            player!!.release()
            initializeMediaPlayer()
        }
        else{
            player!!.stop()
            player!!.release()
            initializeMediaPlayer()
        }
        buttonPlay!!.isEnabled = true

    }

    private fun initializeMediaPlayer() {
        val url = "http://78.58.52.217:8000" // your URL here
//        val url = "https://listen.radioking.com/radio/506872/stream/564490" // your URL here

         player = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
        player!!.setDataSource(url)


    }

    override fun onPause() {
        super.onPause()
        if (player!!.isPlaying) {
            player!!.stop()
        }
    }
}
