package com.trainerview.app.base

import androidx.fragment.app.Fragment
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