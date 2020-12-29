package com.example.themoviedb.api.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ReviewResponse {
    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("page")
    @Expose
    private var page: Int? = null

    @SerializedName("results")
    @Expose
    private var results: List<Result> = arrayListOf()

    @SerializedName("total_pages")
    @Expose
    private var totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    private var totalResults: Int? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getPage(): Int? {
        return page
    }

    fun setPage(page: Int?) {
        this.page = page
    }

    fun getResults(): List<Result> {
        return results
    }

    fun setResults(results: List<Result>) {
        this.results = results
    }

    fun getTotalPages(): Int? {
        return totalPages
    }

    fun setTotalPages(totalPages: Int?) {
        this.totalPages = totalPages
    }

    fun getTotalResults(): Int? {
        return totalResults
    }

    fun setTotalResults(totalResults: Int?) {
        this.totalResults = totalResults
    }

    class Result {
        @SerializedName("author")
        @Expose
        var author: String? = null

        @SerializedName("author_details")
        @Expose
        var authorDetails: AuthorDetails? = null

        @SerializedName("content")
        @Expose
        var content: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("url")
        @Expose
        var url: String? = null

        class AuthorDetails {
            @SerializedName("name")
            @Expose
            var name: String? = null

            @SerializedName("username")
            @Expose
            var username: String? = null

            @SerializedName("avatar_path")
            @Expose
            var avatarPath: Any? = null

            @SerializedName("rating")
            @Expose
            var rating: Double? = null
        }
    }
}