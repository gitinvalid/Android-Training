package com.example.m07

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerAdapter (private val downloads: List<Download>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val downloadName: TextView = view.downloadItem
        private val downloadStatus: TextView = view.status
        fun bind(download: Download) {
            downloadName.text = download.url
            downloadStatus.text = download.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = downloads.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(downloads[position])
}