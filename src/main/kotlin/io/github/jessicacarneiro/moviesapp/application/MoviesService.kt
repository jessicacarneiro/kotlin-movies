package io.github.jessicacarneiro.moviesapp.application

import io.github.jessicacarneiro.moviesapp.domain.Movie
import io.github.jessicacarneiro.moviesapp.infrastructure.KMongoRepository
import org.springframework.stereotype.Service

@Service
class MoviesService(val repository: KMongoRepository) {
    fun getAllMovies() : List<Movie> {
         return repository.getMovies().toList();
    }
}
