package ru.btelepov.moviemix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.btelepov.moviemix.R
import ru.btelepov.moviemix.models.credits.Cast
import ru.btelepov.moviemix.models.credits.CreditsResponse
import ru.btelepov.moviemix.utils.Constants.Companion.POSTER_URL
import ru.btelepov.moviemix.utils.CustomDiffUtil


class CastMovieAdapter : RecyclerView.Adapter<CastMovieAdapter.MyViewHolder>() {

    private var castList = emptyList<Cast>()

    fun setData(newData: CreditsResponse) {
        val movieDiffUtil = CustomDiffUtil(castList, newData.cast)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        castList = newData.cast
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cast_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCast = castList[position]
        Picasso.get().load(POSTER_URL + currentCast.profilePath)
            .placeholder(R.drawable.progress_animation).error(R.drawable.ic_error)
            .into(holder.imageCast)
        holder.nameCast.text = currentCast.name.toString().trim()
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageCast: ImageView = view.findViewById(R.id.castItem_image)
        val nameCast: TextView = view.findViewById(R.id.castItem_name)

    }
}