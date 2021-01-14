package com.example.themoviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.BR
import com.example.themoviedb.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.example.themoviedb.api.response.MovieResponse
import com.example.themoviedb.databinding.ModelListMoviesTopRatedBinding
import com.example.themoviedb.utils.Utils
import kotlinx.android.synthetic.main.model_list_movies_top_rated.view.*
import java.lang.Exception

class MoviesAdapter(private val mContext: Context, private val list: List<MovieResponse>, private var onClickItem: MoviesAdapter.onItemClick) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding : ModelListMoviesTopRatedBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.model_list_movies_top_rated, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.size


    class ViewHolder(val binding: ModelListMoviesTopRatedBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Any) {
            binding.setVariable(BR.mv, data)
            binding.executePendingBindings()
        }
    }

    interface onItemClick{
       fun itemClick(item : MovieResponse);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemListModel : MovieResponse = list!![position]
        itemListModel.releaseDate = Utils.changeDateFormat(itemListModel.releaseDate.toString())
        holder.bind(itemListModel)
/*
        holder.itemView.setVariable(BR.mv, itemListModel) //BR - generated class; BR.user - 'user' is variable name declared in layout
        holder.itemView.executePendingBindings()*/

        Picasso.get().load("https://image.tmdb.org/t/p/w500/${itemListModel.posterPath}")
            .into(holder.itemView.img_movie, object : Callback{
                override fun onSuccess() {
                    holder.itemView.img_movie.scaleType = ImageView.ScaleType.CENTER_CROP;
                }

                override fun onError(e: Exception?) {
                }

            })

        /*holder.itemView.txt_title.text = itemListModel.title
        holder.itemView.txt_release.text = Utils.changeDateFormat(itemListModel.releaseDate.toString())*/

        holder.itemView.cardView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onClickItem.itemClick(itemListModel)
            }

        })
    }
}