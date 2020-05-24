package com.example.m07

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    companion object {
        val statusList = listOf("success", "failed")
        val initList = listOf(
            Download("item1", "waiting"),
            Download("item2", "waiting"),
            Download("item3", "waiting"),
            Download("item4", "waiting"),
            Download("item5", "waiting")
        )
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var downloads: ArrayList<Download> = arrayListOf()

    private lateinit var handlerThread: HandlerThread
    private lateinit var workHandler: Handler
    private val uiHandler = UIHandler(this)

    class UIHandler(activity: MainActivity) : Handler() {
        private val mActivity: WeakReference<MainActivity> = WeakReference(activity)
        override fun handleMessage(message: Message) {
            val pairMessage = message.obj as Pair<*, *>
            mActivity.get()?.run {
                downloads.forEach {
                    if (it.url == pairMessage.first) {
                        it.status = pairMessage.second as String
                    }
                }
                viewAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloads.addAll(initList)
        initRecyclerView()
        initHandlerThread()
    }

    private fun initHandlerThread() {
        handlerThread = HandlerThread("Downloader")
        handlerThread.start()

        workHandler = object : Handler(handlerThread.looper) {
            override fun handleMessage(message: Message) {
                val url = message.obj as String
                Thread.sleep(1000)
                val uiMessage = Message.obtain()
                uiMessage.obj = Pair(url, statusList.shuffled().first())
                uiHandler.sendMessage(uiMessage)
            }
        }
    }

    private fun initRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerAdapter(downloads)

        recyclerView = recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        start.setOnClickListener {
            resetDownload()
            startDownload()
        }

        restart.setOnClickListener {
            workHandler.removeCallbacksAndMessages(null)
            workHandler.post {
                resetDownload()
                startDownload()
            }
        }
    }

    private fun resetDownload() {
        uiHandler.post {
            downloads.forEach {
                it.status = "waiting"
            }
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun startDownload() {
        downloads.forEach {
            val message = Message.obtain()
            message.obj = it.url
            workHandler.sendMessage(message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quitSafely()
    }
}
