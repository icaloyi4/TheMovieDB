package com.example.themoviedb.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataResponse<T> {
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("results")
    @Expose
    var results: T? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null


}