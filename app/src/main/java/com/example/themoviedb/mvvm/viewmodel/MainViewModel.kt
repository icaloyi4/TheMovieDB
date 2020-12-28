package com.example.themoviedb.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themoviedb.api.LoadingState
import com.example.themoviedb.api.response.DataResponse
import com.example.themoviedb.api.response.GenreResponse
import com.example.themoviedb.api.response.MovieResponse
import com.example.themoviedb.mvvm.model.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val repo: MainRepository) : ViewModel() {
    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _movieData = MutableLiveData<DataResponse<List<MovieResponse>>>()
    val movieData: LiveData<DataResponse<List<MovieResponse>>>
        get() = _movieData

    private val _genreData = MutableLiveData<GenreResponse>()
    val genreData: LiveData<GenreResponse>
        get() = _genreData


    val callbackMovie : Callback<DataResponse<List<MovieResponse>>> = object : Callback<DataResponse<List<MovieResponse>>>{
        override fun onFailure(call: Call<DataResponse<List<MovieResponse>>>, t: Throwable) {
            _loadingState.postValue(LoadingState.error(t.message))
        }

        override fun onResponse(call: Call<DataResponse<List<MovieResponse>>>, response: Response<DataResponse<List<MovieResponse>>>) {
            if (response.isSuccessful) {
                _movieData.postValue(response.body())
                _loadingState.postValue(LoadingState.LOADED)
            } else {
                _loadingState.postValue(LoadingState.error(response.errorBody().toString()))
            }
        }

    }

    val callbackGenre : Callback<GenreResponse> = object : Callback<GenreResponse>{
        override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
            _loadingState.postValue(LoadingState.error(t.message))
        }

        override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
            if (response.isSuccessful) {
                _genreData.postValue(response.body())
                _loadingState.postValue(LoadingState.LOADED)
            } else {
                _loadingState.postValue(LoadingState.error(response.errorBody().toString()))
            }
        }

    }


    init {
        fetchData(1)
    }

    private fun fetchData(page : Int) {
        _loadingState.postValue(LoadingState.LOADING)
//        repo.getMovieByGenre(page,"").enqueue(callbackMovie)
        repo.getMovieGenre().enqueue(callbackGenre)
    }
}