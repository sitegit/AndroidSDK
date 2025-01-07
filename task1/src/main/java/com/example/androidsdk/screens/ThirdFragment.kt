package com.example.androidsdk.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.androidsdk.R

class ThirdFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_third, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation(view)
    }

    private fun setupNavigation(view: View) {
        view.findViewById<Button>(R.id.nextBtn).setOnClickListener { navigateToNext() }
        view.findViewById<Button>(R.id.prevBtn).setOnClickListener { navigateToPrevious() }
    }
}