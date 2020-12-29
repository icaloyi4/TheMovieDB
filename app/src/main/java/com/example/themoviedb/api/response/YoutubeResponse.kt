package com.example.themoviedb.api.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class YoutubeResponse {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("results")
    @Expose
    private var results: List<Result> = arrayListOf()

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getResults(): List<Result> {
        return results
    }

    fun setResults(results: List<Result>) {
        this.results = results
    }

    class Result {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("iso_639_1")
        @Expose
        var iso6391: String? = null

        @SerializedName("iso_3166_1")
        @Expose
        var iso31661: String? = null

        @SerializedName("key")
        @Expose
        var key: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("site")
        @Expose
        var site: String? = null

        @SerializedName("size")
        @Expose
        var size: Int? = null

        @SerializedName("type")
        @Expose
        var type: String? = null
    }
}