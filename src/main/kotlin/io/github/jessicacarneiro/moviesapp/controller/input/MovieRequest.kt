package io.github.jessicacarneiro.moviesapp.controller.input

import io.github.jessicacarneiro.moviesapp.domain.Movie

data class MovieRequest(val title: String, val year : Int, val score: Double) {
    fun toMovie() : Movie {
        return Movie(title, year, score)
    }
}
