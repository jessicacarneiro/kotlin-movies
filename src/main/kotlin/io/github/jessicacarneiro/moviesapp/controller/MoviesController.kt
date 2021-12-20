package io.github.jessicacarneiro.moviesapp.controller

import io.github.jessicacarneiro.moviesapp.application.MoviesService
import io.github.jessicacarneiro.moviesapp.controller.input.MovieRequest
import io.github.jessicacarneiro.moviesapp.domain.Movie
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies")
class MoviesController(val service : MoviesService) {

    @GetMapping
    @ResponseBody
    fun getAllMovies() : ResponseEntity<List<Movie>> {
        val movies = service.getAllMovies()

        if (movies.isEmpty()) {
            return ResponseEntity(emptyList(), HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(movies, HttpStatus.OK);
    }

    @PostMapping
    fun createMovie(@RequestBody request : MovieRequest): ResponseEntity<Void> {
        service.addMovie(request.toMovie());
        return ResponseEntity.ok().build();
    }
}
