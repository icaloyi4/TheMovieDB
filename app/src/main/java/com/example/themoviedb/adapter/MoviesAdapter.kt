package com.example.themoviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.example.themoviedb.api.response.MovieResponse
import com.example.themoviedb.utils.Utils
import kotlinx.android.synthetic.main.model_list_movies_top_rated.view.*
import java.lang.Exception

class MoviesAdapter(private val mContext: Context, private val list: List<MovieResponse>, private var onClickItem: MoviesAdapter.onItemClick) : RecyclerView.Adapter<MoviesAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.model_list_movies_top_rated,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val itemListModel : MovieResponse = list!![position]

        Picasso.get().load("https://image.tmdb.org/t/p/w500/${itemListModel.posterPath}")
            .into(holder.view.img_movie, object : Callback{
                override fun onSuccess() {
                    holder.view.img_movie.scaleType = ImageView.ScaleType.CENTER_CROP;
                }

                override fun onError(e: Exception?) {
                }

            })

        holder.view.txt_title.text = itemListModel.title
        holder.view.txt_release.text = Utils.changeDateFormat(itemListModel.releaseDate.toString())

        holder.view.cardView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onClickItem.itemClick(itemListModel)
            }

        })


    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    interface onItemClick{
       fun itemClick(item : MovieResponse);
    }
}