package com.example.themoviedb.utils

interface ScrollViewListener {
    fun onScrollChanged(
        scrollView: ScrollViewExt?,
        x: Int, y: Int, oldx: Int, oldy: Int
    )
}