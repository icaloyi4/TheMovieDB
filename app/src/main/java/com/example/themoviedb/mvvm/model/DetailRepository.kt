package com.example.themoviedb.mvvm.model

import com.example.themoviedb.api.ApiClient

class DetailRepository(private val api: ApiClient) {
    fun getReview(page : Int, id : String) = api.getReview(id, page )
    fun getYoutube(id:String) = api.getYTVid(id)

}