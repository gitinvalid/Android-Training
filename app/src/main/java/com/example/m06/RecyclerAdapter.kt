package com.example.m06

import android.text.SpannableString
import android.text.Spanned
import android.text.style.LeadingMarginSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.m06.module.PopularMovie
import com.example.m06.module.PopularMovies


class RecyclerAdapter(private val popularMovies: PopularMovies) :
    RecyclerView.Adapter<RecyclerAdapter.VH>() {
    class VH(view: View) : ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val title: TextView = view.findViewById(R.id.title)
        private val overview: TextView = view.findViewById(R.id.overview)
        private val rate: TextView = view.findViewById(R.id.rate)

        fun updateInfo(popularMovie: PopularMovie) {
            Glide.with(this.imageView.context)
                .load("https://image.tmdb.org/t/p/w500${popularMovie.poster_path}")
                .into(imageView)
            title.text = popularMovie.title
            rate.text = popularMovie.vote_average.toString()
            val spannableString = SpannableString(popularMovie.overview)
            spannableString.setSpan(
                LeadingMarginSpan.Standard(20, 0), 0,
                popularMovie.overview.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            overview.text = spannableString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.updateInfo(popularMovies.results[position])

    override fun getItemCount() = popularMovies.results.size

}