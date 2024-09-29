package com.trainerview.app.di

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import dagger.Reusable
import javax.inject.Inject

@Reusable
class ArgumentsHandler @Inject constructor() {

    var arguments: Bundle? = null
        internal set

    @VisibleForTesting
    fun setArgs(args: Bundle?) {
        arguments = args
    }
}