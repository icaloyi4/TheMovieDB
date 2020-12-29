package com.example.themoviedb.mvvm.view

import android.content.Intent
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
    private val m by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m.ctx = this

//        supportActionBar?.title = "Home"
        var genre : GenreResponse.Genre = GenreResponse.Genre()
        genre.id = 0
        genre.name = "Select Genre ..."
        m.listGenre.add(genre)
        m.adapterGenre = ArrayAdapter(m.ctx, android.R.layout.simple_list_item_1, m.listGenre)
        spin_genre.adapter = m.adapterGenre
        spin_genre.onItemSelectedListener = this

        initRecycleLayout()
        fetchDataAll()
    }

    private fun fetchDataAll() {
        m.movieData.observe(this, Observer {
            it.results?.let { it1 -> m.listMovie.addAll(it1) }
            m.adapter!!.notifyDataSetChanged()
        })

        m.genreData.observe(this, Observer {
            it.getGenres()?.let { it1 -> m.listGenre.addAll(it1) }
            m.adapterGenre!!.notifyDataSetChanged()
        })


        m.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.FAILED -> {
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    pb.visibility = View.GONE
                }
                LoadingState.Status.RUNNING -> {
                    pb.visibility = View.VISIBLE
                }
                LoadingState.Status.SUCCESS -> {
                    pb.visibility = View.GONE
                }
            }
        })
    }

    private fun initRecycleLayout() {

        val numberOfColumns = 2
        val layoutManager = GridLayoutManager(this, numberOfColumns)
        lv_movie.layoutManager = layoutManager

        lv_movie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    var visibleItemCountPopular = layoutManager.childCount
                    var totalItemCountPopular = layoutManager.itemCount
                    var pastVisiblesItemsPopular = layoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCountPopular + pastVisiblesItemsPopular >= totalItemCountPopular) {
//                        Toast.makeText(ctx, "Turun", Toast.LENGTH_SHORT).show()
                        m.countMovie++
                        m.repo.getMovieByGenre(m.countMovie,"")
                            .enqueue(m.callbackMovie)
                    }
                }
            }
        })

        m.adapter = MoviesAdapter(this, m.listMovie, this)
        lv_movie.adapter = m.adapter
    }

    override fun itemClick(item: MovieResponse) {

        val details = item
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra("movie", details)
        var genre = ""
        if (item.genreIds!=null){
            for(i in item.genreIds!!.indices){
                for(j in m.listGenre.indices){
                    if (item.genreIds!![i]==m.listGenre[j].id){
                        if ((i+1)==item.genreIds!!.size){
                            genre += " ${m.listGenre[j].name}"
                        } else {
                            genre += " ${m.listGenre[j].name},"
                        }
                        break
                    }
                }
            }
        }
        i.putExtra("genre", genre)
        startActivity(i)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p2==0){
            m.countMovie = 1
            m.listMovie.clear()
            m.repo.getMovieByGenre(m.countMovie,"")
                .enqueue(m.callbackMovie)
            txt_genre.text = "All Movie"
        } else {
            m.countMovie = 1
            m.listMovie.clear()
            m.repo.getMovieByGenre(m.countMovie,m.listGenre[p2].id.toString())
                .enqueue(m.callbackMovie)
            txt_genre.text = m.listGenre[p2].name.toString()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}
