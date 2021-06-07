package ru.btelepov.moviemix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.btelepov.moviemix.R
import ru.btelepov.moviemix.models.genres.GenreData
import ru.btelepov.moviemix.utils.CustomDiffUtil

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {

    private var genreList = emptyList<GenreData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.genre_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentGenre = genreList[position]
        holder.name.text = currentGenre.name.trim()
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    fun setData(newData: List<GenreData>) {
        val movieDiffUtil = CustomDiffUtil(genreList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        genreList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.genre_name)
    }
}