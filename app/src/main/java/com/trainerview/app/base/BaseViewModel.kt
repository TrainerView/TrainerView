package com.trainerview.app.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import com.trainerview.app.di.ArgumentsHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    @PublishedApi
    internal lateinit var argumentsHandler: ArgumentsHandler

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun CoroutineScope.safeLaunch(launchBody: suspend () -> Unit): Job {
        return launch(coroutineExceptionHandler) {
            launchBody.invoke()
        }
    }

    inline fun <reified Args : NavArgs> navArgs() = NavArgsLazy(Args::class) {
        argumentsHandler.arguments
            ?: throw IllegalStateException("Fragment $this has null arguments")
    }
}