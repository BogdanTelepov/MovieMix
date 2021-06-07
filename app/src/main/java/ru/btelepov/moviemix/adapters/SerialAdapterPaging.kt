package ru.btelepov.moviemix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.btelepov.moviemix.R
import ru.btelepov.moviemix.models.serials.SerialItem
import ru.btelepov.moviemix.utils.Constants.Companion.POSTER_URL
import ru.btelepov.moviemix.utils.DiffUtilCallBack

class SerialAdapterPaging() :
    PagingDataAdapter<SerialItem, SerialAdapterPaging.MyViewHolder>(DiffUtilCallBack()) {


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { serial ->
            holder.bindSerial(serial)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.serial_row_layout, parent, false)
        return MyViewHolder(view)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.itemSerial_cover)

        fun bindSerial(content: SerialItem) {
            Picasso.get().load(POSTER_URL + content.posterPath).into(imageView)
        }
    }


}