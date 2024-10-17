package com.trainerview.app.base.di

interface ScreenComponent {
    val viewModelFactory: ViewModelFactory
    val argumentsHandler: ArgumentsHandler
}
