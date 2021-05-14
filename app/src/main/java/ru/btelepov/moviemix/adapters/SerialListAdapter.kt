package ru.btelepov.moviemix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.btelepov.moviemix.R

import ru.btelepov.moviemix.models.serials.SerialItem
import ru.btelepov.moviemix.models.serials.SerialResponse
import ru.btelepov.moviemix.utils.Constants.Companion.IMAGE_URL
import ru.btelepov.moviemix.utils.CustomDiffUtil

class SerialListAdapter : RecyclerView.Adapter<SerialListAdapter.MyViewHolder>() {


    private val differCallBack = object : DiffUtil.ItemCallback<SerialItem>() {
        override fun areItemsTheSame(oldItem: SerialItem, newItem: SerialItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SerialItem, newItem: SerialItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.serial_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentSerial = differ.currentList[position]
        Glide.with(holder.itemView.context).load(IMAGE_URL + currentSerial.posterPath)
            .into(holder.imageCover)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageCover: ImageView = view.findViewById(R.id.itemSerial_cover)

    }
}