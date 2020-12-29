package com.example.themoviedb.mvvm.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.adapter.ReviewAdapter
import com.example.themoviedb.api.LoadingState
import com.example.themoviedb.api.response.MovieResponse
import com.example.themoviedb.api.response.ReviewResponse
import com.example.themoviedb.mvvm.viewmodel.DetailViewModel
import com.example.themoviedb.utils.ScrollViewExt
import com.example.themoviedb.utils.ScrollViewListener
import com.example.themoviedb.utils.Utils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity(), ScrollViewListener {

    private var countReview: Int = 1
    private val mv by viewModel<DetailViewModel>()

    lateinit var context : Context

    var idYT = ""

    var adapterReview : ReviewAdapter? = null
    var listReview : ArrayList<ReviewResponse.Result> = arrayListOf()
    var model : MovieResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        context = this;

        lin_back.setOnClickListener { finish()}

        model = intent.getSerializableExtra("movie") as MovieResponse?
        txt_genre.text = intent.getStringExtra("genre")

        if (model!=null) {
            init()
            fetchData()
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun init(){

        Picasso.get().load("https://image.tmdb.org/t/p/w500/${model!!.backdropPath}")
            .into(img_foto, object : Callback {
                override fun onSuccess() {
                    img_foto.scaleType = ImageView.ScaleType.CENTER_CROP;
                }

                override fun onError(e: Exception?) {

                }

            })
        txt_title.text = model!!.title.toString()
        txt_release.text = Utils.changeDateFormat(model!!.releaseDate.toString())
        txt_content.text = model!!.overview.toString()

        img_play.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if (idYT.isNotEmpty()) {
                    val i  = Intent(context, YoutubeActivity::class.java)
                    i.putExtra("idYt", idYT)
                    startActivity(i)
                } else {
                    Toast.makeText(context, "No Video Found", Toast.LENGTH_SHORT).show()
                }

            }

        })

    }

    private fun fetchData() {

        mv.reviewData.observe(this, Observer {
            if (it.getResults().isNotEmpty()) {
                txt_no_review.visibility = View.GONE
                listReview.addAll(it.getResults())
            }
            adapterReview!!.notifyDataSetChanged()
        })

        mv.ytData.observe(this, Observer {
            if (it.getResults().isNotEmpty()) {
                it.getResults().forEach { yt ->
                    if (yt.key!=null){
                        idYT = yt.key!!
                    }

                }

                if (idYT.isNotEmpty()){
                    img_play.visibility = View.VISIBLE
                } else {
                    img_play.visibility = View.GONE
                }
            }
        })

        mv.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.FAILED -> {
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
                LoadingState.Status.RUNNING -> {
                    progressBar.visibility = View.VISIBLE
                }
                LoadingState.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onStart() {
        mv.repo.getReview(countReview, model!!.id.toString())
            .enqueue(mv.callbackReview)
        initRecycle()

        mv.repo.getYoutube(model!!.id.toString()).enqueue(mv.callbackYt)
        super.onStart()
    }

    private fun initRecycle() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        lv_review.layoutManager = layoutManager

        adapterReview = ReviewAdapter(this, listReview)
        lv_review.adapter = adapterReview
/*
        lv_review.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)  //check for scroll down
                {
                    var visibleItemCountReview = layoutManager.getChildCount()
                    var totalItemCountReview = layoutManager.getItemCount()
                    var pastVisiblesItemsReview = layoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCountReview + pastVisiblesItemsReview >= totalItemCountReview) {
                        *//*countReview++
                        mv.repo.getReview(countReview, model!!.id.toString())
                            .enqueue(mv.callbackReview)*//*
                        Toast.makeText(context, "Turun", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })*/

        lv_review.setNestedScrollingEnabled(false)

        sv.setScrollViewListener(this)
    }

    override fun onScrollChanged(scrollView: ScrollViewExt?, x: Int, y: Int, oldx: Int, oldy: Int) {
        // We take the last son in the scrollview

        // We take the last son in the scrollview
        val view = scrollView!!.getChildAt(scrollView!!.childCount - 1) as View
        val diff = view.bottom - (scrollView!!.height + scrollView!!.scrollY)

        // if diff is zero, then the bottom has been reached

        // if diff is zero, then the bottom has been reached
        if (diff == 0) {
//            Toast.makeText(context, "Turun", Toast.LENGTH_SHORT).show()
            countReview++
            mv.repo.getReview(countReview, model!!.id.toString())
                .enqueue(mv.callbackReview)
        }
    }
}
