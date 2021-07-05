package io.github.jessicacarneiro.moviesapp.controller

import io.github.jessicacarneiro.moviesapp.domain.Movie
import io.github.jessicacarneiro.moviesapp.infrastructure.KMongoRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("movies")
class MoviesController {

    @GetMapping
    fun getAllMovies() : Array<Movie> {
        return emptyArray<Movie>()
    }
}