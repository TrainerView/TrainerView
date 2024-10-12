package com.trainerview.app.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

interface NavEvent {

    fun navigate(fragment: Fragment)
}

class NavigateTo(
    private val direction: NavDirections
) : NavEvent {
    override fun navigate(fragment: Fragment) {
        fragment.findNavController().navigate(direction)
    }
}

class BackWithResult(
    private val requestKey: String,
    private val result: Bundle
): NavEvent {
    override fun navigate(fragment: Fragment) {
        fragment.setFragmentResult(requestKey, result)
        fragment.findNavController().popBackStack()
    }
}