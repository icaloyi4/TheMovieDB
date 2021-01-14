package com.example.themoviedb.adapter

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.BR
import com.example.themoviedb.R
import com.example.themoviedb.api.response.ReviewResponse
import com.example.themoviedb.databinding.ModelReviewBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.model_review.view.*


class ReviewAdapter(private val mContext: Context, private val list: List<ReviewResponse.Result>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ModelReviewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.model_review, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemListModel : ReviewResponse.Result = list[position]

        holder.bind(itemListModel)

            Picasso.get().load("https://image.tmdb.org/t/p/w500/${itemListModel.authorDetails?.avatarPath}")
                .into(holder.itemView.img_foto, object : Callback {
                    override fun onSuccess() {
                        val imageBitmap = ( holder.itemView.img_foto.drawable as BitmapDrawable).bitmap
                        val imageDrawable =
                            RoundedBitmapDrawableFactory.create(mContext.resources, imageBitmap)
                        imageDrawable.isCircular = true
                        imageDrawable.cornerRadius =
                            Math.max(imageBitmap.width, imageBitmap.height) / 2.0f
                        holder.itemView.img_foto.setImageDrawable(imageDrawable)
                        holder.itemView.img_foto.scaleType = ImageView.ScaleType.CENTER_CROP;
                    }

                    override fun onError(e: Exception?) {
                        holder.itemView.img_foto.setImageResource(R.drawable.ic_action_favorit_light);
                    }

                })

        /*holder.view.txt_author.text = itemListModel.author
        holder.view.txt_update.text = itemListModel.updatedAt
        holder.view.txt_content.text = itemListModel.content*/



    }

    class ViewHolder(val binding: ModelReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Any) {
            binding.review = data as ReviewResponse.Result?
            binding.executePendingBindings()
        }
    }
}