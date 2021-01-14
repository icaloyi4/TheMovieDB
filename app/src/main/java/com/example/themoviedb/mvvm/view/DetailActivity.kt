package com.example.themoviedb.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.adapter.ReviewAdapter
import com.example.themoviedb.api.LoadingState
import com.example.themoviedb.api.response.MovieResponse
import com.example.themoviedb.mvvm.factory.DetailViewModelFactory
import com.example.themoviedb.mvvm.viewmodel.DetailViewModel
import com.example.themoviedb.utils.ScrollViewExt
import com.example.themoviedb.utils.ScrollViewListener
import com.example.themoviedb.utils.Utils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class DetailActivity : AppCompatActivity(), ScrollViewListener, CoroutineScope {

//    private val m by viewModel<DetailViewModel>()

    lateinit var m : DetailViewModel
    lateinit var job : Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var detailViewModelFactory = DetailViewModelFactory()
        m = ViewModelProviders.of(this,detailViewModelFactory).get(DetailViewModel::class.java)

        job = Job()
        m.context = this;

        lin_back.setOnClickListener { finish()}

        m.model = intent.getSerializableExtra("movie") as MovieResponse?
        txt_genre.text = intent.getStringExtra("genre")

        if (m.model!=null) {
            init()
            fetchData()
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun init(){

        Picasso.get().load("https://image.tmdb.org/t/p/w500/${m.model!!.backdropPath}")
            .into(img_foto, object : Callback {
                override fun onSuccess() {
                    img_foto.scaleType = ImageView.ScaleType.CENTER_CROP;
                }

                override fun onError(e: Exception?) {

                }

            })
        txt_title.text = m.model!!.title.toString()
        txt_release.text = Utils.changeDateFormat(m.model!!.releaseDate.toString())
        txt_content.text = m.model!!.overview.toString()

        img_play.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if (m.idYT.isNotEmpty()) {
                    val i  = Intent(m.context, YoutubeActivity::class.java)
                    i.putExtra("idYt", m.idYT)
                    startActivity(i)
                } else {
                    Toast.makeText(m.context, "No Video Found", Toast.LENGTH_SHORT).show()
                }

            }

        })

    }

    private fun fetchData() {

        m.reviewData.observe(this, Observer {
            if (it.getResults().isNotEmpty()) {
                txt_no_review.visibility = View.GONE
                m.listReview.addAll(it.getResults())
            }
            m.adapterReview?.notifyDataSetChanged()
        })

        m.ytData.observe(this, Observer {
            if (it.getResults().isNotEmpty()) {
                it.getResults().forEach { yt ->
                    if (yt.key!=null){
                        m.idYT = yt.key!!
                    }

                }

                if (m.idYT.isNotEmpty()){
                    img_play.visibility = View.VISIBLE
                } else {
                    img_play.visibility = View.GONE
                }
            }
        })

        m.loadingState.observe(this, Observer {
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
        launch {
            m.repo.getReview(m.countReview, m.model!!.id.toString())
                .enqueue(m.callbackReview)
            initRecycle()
            m.repo.getYoutube(m.model!!.id.toString()).enqueue(m.callbackYt)
        }
        super.onStart()
    }

    private fun initRecycle() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        lv_review.layoutManager = layoutManager

        m.adapterReview = ReviewAdapter(this, m.listReview)
        lv_review.adapter = m.adapterReview

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
            m.countReview++
            launch {
                m.repo.getReview(m.countReview, m.model!!.id.toString())
                    .enqueue(m.callbackReview)
            }
        }
    }
}
