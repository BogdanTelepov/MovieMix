package ru.btelepov.moviemix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import ru.btelepov.moviemix.R
import ru.btelepov.moviemix.models.Movie
import ru.btelepov.moviemix.models.MovieResponse
import ru.btelepov.moviemix.utils.CustomDiffUtil

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    private var movies = emptyList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovie = movies[position]
        Glide.with(holder.itemView.context).load(currentMovie.posterPath).into(holder.imageCover)

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setData(newData: MovieResponse) {
        val movieDiffUtil = CustomDiffUtil(movies, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        movies = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageCover: ImageView = view.findViewById(R.id.itemMovie_cover)

    }
}