package ru.btelepov.moviemix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.btelepov.moviemix.R
import ru.btelepov.moviemix.models.movies.MovieData
import ru.btelepov.moviemix.utils.Constants.Companion.BACKDROP_PATH_URL

class ViewPagerAdapter() :
    RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    private var movies = emptyList<MovieData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        val currentMovie = movies[position]
        Glide.with(holder.itemView.context).load(BACKDROP_PATH_URL + currentMovie.backdropPath)
            .into(holder.imageItem)
    }

    override fun getItemCount(): Int {
        return movies.size / 2
    }

    fun fetchData(newData: List<MovieData>) {
        this.movies = newData
        notifyDataSetChanged()
    }

    inner class Pager2ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageItem: ImageView = view.findViewById(R.id.imageItem_view_pager)
    }
}