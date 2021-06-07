package ru.btelepov.moviemix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.btelepov.moviemix.R

import ru.btelepov.moviemix.models.serials.SerialItem
import ru.btelepov.moviemix.utils.Constants.Companion.POSTER_URL
import ru.btelepov.moviemix.utils.DiffUtilCallBack

class SerialListAdapter : RecyclerView.Adapter<SerialListAdapter.MyViewHolder>() {


    val differ = AsyncListDiffer(this, DiffUtilCallBack())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.serial_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentSerial = differ.currentList[position]
        Glide.with(holder.itemView.context).load(POSTER_URL + currentSerial.posterPath)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.ic_error)
            .into(holder.imageCover)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageCover: ImageView = view.findViewById(R.id.itemSerial_cover)

    }
}