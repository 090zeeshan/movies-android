package com.vd.movies.ui

interface MainActivityDelegate {
    fun setTitle(title: String)
    fun enableDrawer(isEnable: Boolean)
}