package ru.lorens.rosbankhack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.lorens.rosbankhack.R
import ru.lorens.rosbankhack.rest.Card


class PreviewRecyclerAdapter(var items: List<Card>, val callback: (Card) -> Unit) :
    RecyclerView.Adapter<PreviewRecyclerAdapter.PreviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PreviewHolder(LayoutInflater.from(parent.context).inflate(R.layout.element_preview, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PreviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val smallImg = itemView.findViewById<ImageView>(R.id.smallImg)

        fun bind(item: Card) {


            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))

            Glide
                .with(smallImg.context)
                .load(item.article.image)
                .apply(requestOptions)
                .centerCrop()
                .into(smallImg)

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback(items[adapterPosition])
            }
        }
    }
}