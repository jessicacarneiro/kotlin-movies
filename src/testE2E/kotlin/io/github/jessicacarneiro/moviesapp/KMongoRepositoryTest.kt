package io.github.jessicacarneiro.moviesapp.infrastructure

import io.github.jessicacarneiro.moviesapp.domain.Movie
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class KMongoRepositoryTest {

    @Autowired
    lateinit var repository : KMongoRepository

    @BeforeEach
    fun setUp() {
        repository.insertMovie(Movie("Blade Runner", 1982, 8.1))
    }

    @AfterEach
    fun tearDown() {
        repository.deleteMovies("{ title: {\$regex : \"Blade Runner.*\"} }")
    }

    @Test
    fun `should retrieve collection Movies`() {
        repository.getMovies().count() `should be` 1
    }

    @Test
    fun `should return empty collection if no movies matching a query`() {
        val query = "{ title: 'Blade Runner 2049' }"

        repository.findMovies(query).count() `should be` 0
    }

    @Test
    fun `should return collection if movies match a query`() {
        val query = "{ title: 'Blade Runner' }"

        repository.findMovies(query).count() `should be` 1
    }

    @Test
    fun `should insert movie with specified values`() {
        val newMovie = Movie("Blade Runner 2049", 2017, 8.0)
        repository.insertMovie(newMovie)

        val movie: Movie? = repository.findMovies("{ title: 'Blade Runner 2049' }").first()

        movie?.title `should be equal to` ("Blade Runner 2049")
        movie?.year `should be equal to` 2017
        movie?.score `should be equal to` 8.0
    }
}