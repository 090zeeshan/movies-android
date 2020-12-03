package com.vd.movies.ui.home

import androidx.lifecycle.MutableLiveData
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.layout_search.*

class HomeViewModel : BaseViewModel("Movies") {
    val searchKey = MutableLiveData("")

    fun onSearchClicked() {
        navigate(HomeFragmentDirections.actionSearchFragment(searchKey.value.toString()))
        searchKey.value = ""
    }

}