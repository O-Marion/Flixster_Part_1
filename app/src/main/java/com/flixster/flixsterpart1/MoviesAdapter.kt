package com.flixster.flixsterpart1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "MoviesAdapter"
private const val MOVIE_BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w500/"

class MoviesAdapter(private val context: Context, private val movies: List<Movies.Result>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val backdropImageView = itemView.findViewById<ImageView>(R.id.movieBackdrop)
        private val titleTextView = itemView.findViewById<TextView>(R.id.movieTitle)
        private val overviewTextView = itemView.findViewById<TextView>(R.id.movieOverview)

        fun bind(movie: Movies.Result) {
            titleTextView.text = movie.title
            overviewTextView.text = movie.overview

            Glide.with(context)
                .load("$MOVIE_BACKDROP_BASE_URL${movie.backdropPath}")
                .into(backdropImageView)
        }
    }
}