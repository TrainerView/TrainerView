package com.trainerview.app.di

interface ScreenComponent {
    val viewModelFactory: ViewModelFactory
    val argumentsHandler: ArgumentsHandler
}