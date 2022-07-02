package com.tutorial.youtubemediaplayer.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.youtubemediaplayer.Adapter.SentenceAdapter
import com.tutorial.youtubemediaplayer.Data.Sentence
import com.tutorial.youtubemediaplayer.Function.ConvertVideoID
import com.tutorial.youtubemediaplayer.JavaScript.Youtube
import com.tutorial.youtubemediaplayer.R

@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class VideoActivity : AppCompatActivity() {
    lateinit var sentences: ArrayList<Sentence>
    private lateinit var adapter: SentenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        findView()
        // Get Sentence
        sentences = intent.getSerializableExtra("sentence") as ArrayList<Sentence>
        // List data
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        adapter = SentenceAdapter(sentences)
        tvEditor.text = "主編輯:${sentences[0].mainEditor}"
        recyclerView.adapter = adapter
        runOnUiThread {}
        adapter.setOnItemClickListener(object : SentenceAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                scrollTo(recyclerView, position)
                webView.loadUrl("javascript:seekTo(" + sentences[position].time.toString() + ")")
            }
        })
        adapter.notifyDataSetChanged()

        // Video
        setWebView()
        touchViewEvent(false)
        val youtubeId: String = ConvertVideoID().convertVideoId(sentences[0].videourl)
        iFrameUrl(youtubeId)
        btnYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(sentences[0].videourl)
            intent.setPackage("com.google.android.youtube")
            startActivity(intent)
        }
        touchView.setOnClickListener { playStop() }
        btnPlayer.setOnClickListener { playStop() }
    }

    // Play and Stop Video
    var btnState = 0
    private fun playStop() {
        when (btnState) {
            0, 2 -> {
                webView.loadUrl("javascript:playVideo()")
                touchViewEvent(false)
            }
            1 -> {
                webView.loadUrl("javascript:pauseVideo()")
                touchViewEvent(true)
            }
        }
    }

    // Setting iFrame Youtube URL
    private fun iFrameUrl(youtubeId: String) {
        val videoEmbeddedAddress: String = Youtube().buildHtml(youtubeId)
        val mimeType = "text/html"
        val encoding = "UTF-8"
        webView.loadUrl(videoEmbeddedAddress)
        webView.loadDataWithBaseURL("", videoEmbeddedAddress, mimeType, encoding, "")
    }

    // Scroll recyclerview function
    @SuppressLint("NotifyDataSetChanged")
    private fun scrollTo(recyclerView: RecyclerView, position: Int) {
        val smoothScroller: RecyclerView.SmoothScroller = object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_START
        }
        runOnUiThread {
            //(recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
            //recyclerView.smoothScrollToPosition(position)
            // Smooth Scroll item to Top
            smoothScroller.targetPosition = position
            recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
            adapter.tagItem(position)
            adapter.notifyDataSetChanged()
        }
    }

    // TouchView show and hide
    fun touchViewEvent(show: Boolean?) {
        if (!show!!) {
            touchViewBackground.setBackgroundColor(Color.parseColor("#00000000"))
            touchViewIcon.visibility = View.INVISIBLE
        } else {
            touchViewBackground.setBackgroundColor(Color.parseColor("#A0000000"))
            touchViewIcon.visibility = View.VISIBLE
        }
    }

    // WebView setting
    @SuppressLint("ClickableViewAccessibility", "SetJavaScriptEnabled", "JavascriptInterface")
    private fun setWebView() {
        val userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/77.0.3865.90 Safari/537.36"
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.settings.userAgentString = userAgent //Important to auto play video
        webView.settings.loadsImagesAutomatically = true
        webView.setOnTouchListener { _, _ -> true }
        webView.addJavascriptInterface(JsObject(), "android")
    }

    // Javascript Interface
    inner class JsObject {
        // Change btnPlayer Icon
        @JavascriptInterface
        fun playerIcon(state: Int) {
            runOnUiThread {
                btnState = state
                when(state) {
                    0 -> btnPlayer.setBackgroundResource(R.drawable.img_player)
                    1 -> btnPlayer.setBackgroundResource(R.drawable.img_pause)
                    2 -> {
                        btnPlayer.setBackgroundResource(R.drawable.img_player)
                        touchViewEvent(true)
                    }
                }
            }
        }
        // Get CurrentTime and Scroll recyclerview
        @JavascriptInterface
        fun getCurrentTime(time: Int) {
            runOnUiThread {
                sentences.forEachIndexed { index, sentence ->
                    if (time == sentence.time) {
                        scrollTo(recyclerView, index)
                    }
                }
            }
        }
    }

    private lateinit var webView: WebView
    private lateinit var btnPlayer: ImageButton
    private lateinit var btnYoutube: ImageButton
    private lateinit var tvEditor: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var touchView: FrameLayout
    private lateinit var touchViewBackground: View
    private lateinit var touchViewIcon: View
    private fun findView() {
        webView = findViewById(R.id.webView)
        btnPlayer = findViewById(R.id.btn_player)
        btnYoutube = findViewById(R.id.btn_youtube)
        tvEditor = findViewById(R.id.tv_editor)
        recyclerView = findViewById(R.id.recyclerview)
        touchView = findViewById(R.id.touchView)
        touchViewBackground = findViewById(R.id.touchView_background)
        touchViewIcon = findViewById(R.id.touchView_icon)
    }

    fun back(view: View) {
        super.onBackPressed()
    }
}