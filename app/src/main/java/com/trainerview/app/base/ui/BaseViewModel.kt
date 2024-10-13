package com.trainerview.app.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import com.trainerview.app.base.di.ArgumentsHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    @PublishedApi
    internal lateinit var argumentsHandler: ArgumentsHandler

    private val _navEvents = MutableSharedFlow<NavEvent?>()
    val navEvents: SharedFlow<NavEvent?> = _navEvents

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun safeLaunch(launchBody: suspend (CoroutineScope) -> Unit): Job {
        return viewModelScope.launch(coroutineExceptionHandler) {
            launchBody.invoke(this)
        }
    }

    protected fun postNavEvents(vararg events: NavEvent) {
        viewModelScope.launch {
            events.forEach { _navEvents.emit(it) }
        }
    }

    inline fun <reified Args : NavArgs> navArgs() = NavArgsLazy(Args::class) {
        argumentsHandler.arguments
            ?: throw IllegalStateException("Fragment $this has null arguments")
    }
}