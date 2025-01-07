package com.example.androidsdk.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.androidsdk.R
import com.example.androidsdk.screens.FirstFragment
import com.example.androidsdk.screens.SecondFragment
import com.example.androidsdk.screens.ThirdFragment

object Router {
    private val navigationMap = mapOf(
        Screen.First to { FirstFragment() },
        Screen.Second to { SecondFragment() },
        Screen.Third to { ThirdFragment() }
    )

    private fun Fragment.toScreen() = when (this) {
        is FirstFragment -> Screen.First
        is SecondFragment -> Screen.Second
        is ThirdFragment -> Screen.Third
        else -> throw IllegalArgumentException("Unknown fragment type")
    }

    fun navigate(fragmentManager: FragmentManager, currentFragment: Fragment, isNext: Boolean) {
        val currentScreen = currentFragment.toScreen()
        val targetScreen = if (isNext) currentScreen.next else currentScreen.previous
        val nextFragment = navigationMap[targetScreen]?.invoke()
            ?: throw IllegalArgumentException("Navigation not found")

        changeFragment(fragmentManager, nextFragment)
    }

    private fun changeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}