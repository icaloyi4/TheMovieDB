package com.example.themoviedb.api.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class GenreResponse {
    @SerializedName("genres")
    @Expose
    private var genres: List<Genre> = arrayListOf()

    fun getGenres(): List<Genre> {
        return genres
    }

    fun setGenres(genres: List<Genre>) {
        this.genres = genres
    }

    class Genre {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        override fun toString(): String {
            return name.toString()
        }
    }
}