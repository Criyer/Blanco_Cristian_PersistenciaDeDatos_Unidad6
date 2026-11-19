package com.example.navigation_blancocristian

import java.io.Serializable

data class Meal(
    val id: Long = 0,
    val category: String,
    val title: String,
    val description: String
) : Serializable
