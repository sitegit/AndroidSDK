package com.example.androidsdk.screens

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.androidsdk.navigation.NavigationHandler
import com.example.androidsdk.navigation.Router

abstract class BaseFragment : Fragment(), NavigationHandler {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressed()
    }

    override fun navigateToNext() {
        Router.navigate(parentFragmentManager, this, true)
    }

    override fun navigateToPrevious() {
        Router.navigate(parentFragmentManager, this, false)
    }

    private fun backPressed() {
        requireActivity().apply {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    moveTaskToBack(true)
                }
            })
        }
    }
}