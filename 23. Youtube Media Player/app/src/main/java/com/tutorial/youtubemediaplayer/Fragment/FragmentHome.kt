package com.tutorial.youtubemediaplayer.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.tutorial.youtubemediaplayer.API.Video
import com.tutorial.youtubemediaplayer.Activity.VideoActivity
import com.tutorial.youtubemediaplayer.Adapter.VideoListAdapter
import com.tutorial.youtubemediaplayer.Data.ObjectData
import com.tutorial.youtubemediaplayer.Data.Sentence
import com.tutorial.youtubemediaplayer.Data.VideoDetail
import com.tutorial.youtubemediaplayer.Function.ConvertTime
import com.tutorial.youtubemediaplayer.R
import okhttp3.*
import java.io.IOException
import java.io.Serializable
import java.util.*

class FragmentHome: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: VideoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // define view
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // main function
        recyclerView = view.findViewById(R.id.recyclerview)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        // Get Data
        getData()
        // Define
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        swipeRefreshLayout.setColorSchemeResources(R.color.blue)
        swipeRefreshLayout.setOnRefreshListener {
            data.clear()
            getData()
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    var data: ArrayList<VideoDetail> = ArrayList()
    var sentence: ArrayList<Sentence> = ArrayList()
    private fun getData() {
        val request: Request = Video().postData()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val objectData = Gson().fromJson(json, ObjectData::class.java)
                // Sentence、VideoURL、MainEditor
                objectData.result.videoInfo.captionResult.results.forEach { results ->
                    results.captions.forEachIndexed { index, captions ->
                        sentence.add(
                            Sentence(
                                captions.content,
                                captions.miniSecond,
                                index + 1,
                                objectData.result.videoInfo.videourl,
                                objectData.result.mainEditor
                            )
                        )
                    }
                }
                requireActivity().runOnUiThread {
                    data.add(
                        VideoDetail(
                            objectData.result.videoInfo.thumbnails,
                            ConvertTime().convertTime(objectData.result.videoInfo.duration),
                            objectData.result.videoInfo.title,
                            objectData.result.viewer
                        )
                    )
                    adapter = VideoListAdapter(data)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    adapter.setOnItemClickListener(object : VideoListAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(activity, VideoActivity::class.java)
                            val bundle = Bundle()
                            bundle.putSerializable("sentence", sentence as Serializable)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }
                    })
                }
            }
        })
    }
}