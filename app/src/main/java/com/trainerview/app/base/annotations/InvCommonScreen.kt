package com.trainerview.app.base.annotations

/**
 * Used to annotate activities, fragments, and dialogs with a screen number
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class InvCommonScreen(val number: Array<String>, val extraInfo: String = "")
