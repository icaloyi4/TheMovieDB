package com.example.themoviedb.mvvm.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.R
import com.example.themoviedb.adapter.MoviesAdapter
import com.example.themoviedb.api.LoadingState
import com.example.themoviedb.api.response.GenreResponse
import com.example.themoviedb.api.response.MovieResponse
import com.example.themoviedb.mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), MoviesAdapter.onItemClick, AdapterView.OnItemSelectedListener {
    private var countMovie: Int = 1
    private val mv by viewModel<MainViewModel>()

    var adapter : MoviesAdapter? = null
    var listMovie : ArrayList<MovieResponse> = arrayListOf()
    lateinit var ctx : Context

    lateinit var adapterGenre : ArrayAdapter<GenreResponse.Genre>
    var listGenre : ArrayList<GenreResponse.Genre> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ctx = this

//        supportActionBar?.title = "Home"
        var genre : GenreResponse.Genre = GenreResponse.Genre()
        genre.id = 0
        genre.name = "Select Genre ..."
        listGenre.add(genre)
        adapterGenre = ArrayAdapter(ctx, android.R.layout.simple_list_item_1, listGenre)
        spin_genre.adapter = adapterGenre
        spin_genre.onItemSelectedListener = this

        initRecycleLayout()
        fetchDataAll()
    }

    private fun fetchDataAll() {
        mv.movieData.observe(this, Observer {
            it.results?.let { it1 -> listMovie.addAll(it1) }
            adapter!!.notifyDataSetChanged()
        })

        mv.genreData.observe(this, Observer {
            it.getGenres()?.let { it1 -> listGenre.addAll(it1) }
            adapterGenre!!.notifyDataSetChanged()
        })


        mv.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.FAILED -> {
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecycleLayout() {

        val numberOfColumns = 2
        val layoutManager = GridLayoutManager(this, numberOfColumns)
        lv_movie.layoutManager = layoutManager
/*
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        lv_movie.layoutManager = layoutManager*/

        lv_movie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    var visibleItemCountPopular = layoutManager.childCount
                    var totalItemCountPopular = layoutManager.itemCount
                    var pastVisiblesItemsPopular = layoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCountPopular + pastVisiblesItemsPopular >= totalItemCountPopular) {
//                        Toast.makeText(ctx, "Turun", Toast.LENGTH_SHORT).show()
                        countMovie++
                        mv.repo.getMovieByGenre(countMovie,"")
                            .enqueue(mv.callbackMovie)
                    }
                }
            }
        })

        adapter = MoviesAdapter(this, listMovie, this)
        lv_movie.adapter = adapter
    }

    override fun itemClick(item: MovieResponse) {

        /*val details = item
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra("movie", details)
        startActivity(i)*/
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p2==0){
            countMovie = 1
            listMovie.clear()
            mv.repo.getMovieByGenre(countMovie,"")
                .enqueue(mv.callbackMovie)
            txt_genre.text = "All Movie"
        } else {
            countMovie = 1
            listMovie.clear()
            mv.repo.getMovieByGenre(countMovie,listGenre[p2].id.toString())
                .enqueue(mv.callbackMovie)
            txt_genre.text = listGenre[p2].name.toString()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
