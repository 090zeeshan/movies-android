package com.vd.movies.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.vd.movies.data.repository.Repository
import com.vd.movies.navigation.NavigationCommand
import com.vd.movies.ui.util.Event

open class BaseViewModel(val repository: Repository, title: String) : ViewModel() {

    constructor(repository: Repository): this(repository, "")

    val title = MutableLiveData(title)
    val navigationCommand = MutableLiveData<Event<NavigationCommand>>(null)

    fun navigate(directions: NavDirections) {
        navigationCommand.postValue(Event(NavigationCommand.To(directions)))
    }
}