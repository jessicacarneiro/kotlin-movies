package io.github.jessicacarneiro.moviesapp.controller

import io.github.jessicacarneiro.moviesapp.application.MoviesService
import io.github.jessicacarneiro.moviesapp.controller.input.MovieRequest
import io.github.jessicacarneiro.moviesapp.domain.Movie
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    val logger: Logger = LoggerFactory.getLogger(MoviesService::class.java)

    @GetMapping
    @ResponseBody
    fun getAllMovies() : ResponseEntity<List<Movie>> {
        val movies = service.getAllMovies()

        if (movies.isEmpty()) {
            logger.debug("No movies found")
            return ResponseEntity(emptyList(), HttpStatus.NO_CONTENT)
        }

        logger.debug("Found movies $movies")
        return ResponseEntity(movies, HttpStatus.OK)
    }

    @PostMapping
    fun createMovie(@RequestBody request : MovieRequest): ResponseEntity<Void> {
        return try {
            service.addMovie(request.toMovie())
            logger.debug("New movie $request inserted")

            ResponseEntity(HttpStatus.CREATED)
        } catch (ex: RuntimeException) {
            logger.error("Unable to create movie", ex)

            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
