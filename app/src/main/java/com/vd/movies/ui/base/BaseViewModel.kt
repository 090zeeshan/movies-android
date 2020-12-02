package com.vd.movies.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.vd.movies.navigation.NavigationCommand
import com.vd.movies.repository.IRepository
import com.vd.movies.repository.Repository
import com.vd.movies.ui.util.Event

open class BaseViewModel(title: String = "") : ViewModel() {
    val title = MutableLiveData(title)
    protected lateinit var repository: IRepository
    val navigationCommand = MutableLiveData<Event<NavigationCommand>>(null)

    fun navigate(directions: NavDirections) {
        navigationCommand.postValue(Event(NavigationCommand.To(directions)))
    }

}