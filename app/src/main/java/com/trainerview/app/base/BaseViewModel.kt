package com.trainerview.app.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun CoroutineScope.safeLaunch(launchBody: suspend () -> Unit): Job {
        return launch(coroutineExceptionHandler) {
            launchBody.invoke()
        }
    }
}