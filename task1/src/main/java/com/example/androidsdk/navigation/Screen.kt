package com.example.androidsdk.navigation

sealed class Screen {
    data object First : Screen()
    data object Second : Screen()
    data object Third : Screen()

    val next: Screen
        get() = when (this) {
            First -> Second
            Second -> Third
            Third -> First
        }

    val previous: Screen
        get() = when (this) {
            First -> Third
            Second -> First
            Third -> Second
        }
}