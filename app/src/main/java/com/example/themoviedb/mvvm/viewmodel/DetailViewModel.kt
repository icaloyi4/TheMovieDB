package com.example.themoviedb.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themoviedb.adapter.ReviewAdapter
import com.example.themoviedb.api.LoadingState
import com.example.themoviedb.api.response.MovieResponse
import com.example.themoviedb.api.response.ReviewResponse
import com.example.themoviedb.api.response.YoutubeResponse
import com.example.themoviedb.mvvm.model.DetailRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(val repo: DetailRepository) : ViewModel()  {

    var countReview: Int = 1
    lateinit var context : Context

    var idYT = ""

    var adapterReview : ReviewAdapter? = null
    var listReview : ArrayList<ReviewResponse.Result> = arrayListOf()
    var model : MovieResponse? = null

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _reviewData = MutableLiveData<ReviewResponse>()
    val reviewData: LiveData<ReviewResponse>
        get() = _reviewData

    val callbackReview: Callback<ReviewResponse> = object :
        Callback<ReviewResponse> {
        override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
            _loadingState.postValue(LoadingState.error(t.message))
        }

        override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
            if (response.isSuccessful) {
                _reviewData.postValue(response.body())
                _loadingState.postValue(LoadingState.LOADED)
            } else {
                _loadingState.postValue(LoadingState.error(response.errorBody().toString()))
            }
        }

    }

    private val _ytData = MutableLiveData<YoutubeResponse>()
    val ytData: LiveData<YoutubeResponse>
        get() = _ytData

    val callbackYt: Callback<YoutubeResponse> = object :
        Callback<YoutubeResponse> {
        override fun onFailure(call: Call<YoutubeResponse>, t: Throwable) {
            _loadingState.postValue(LoadingState.error(t.message))
        }

        override fun onResponse(call: Call<YoutubeResponse>, response: Response<YoutubeResponse>) {
            if (response.isSuccessful) {
                _ytData.postValue(response.body())
                _loadingState.postValue(LoadingState.LOADED)
            } else {
                _loadingState.postValue(LoadingState.error(response.errorBody().toString()))
            }
        }

    }
}

