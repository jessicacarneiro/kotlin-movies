package io.github.jessicacarneiro.moviesapp.infrastructure

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import io.github.jessicacarneiro.moviesapp.domain.Movie
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.litote.kmongo.deleteMany
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KMongoRepositoryTest {

    val repository = KMongoRepository()

    @BeforeEach
    fun setUp() {
        repository.getMovies().insertOne(Movie("Blade Runner", 1982, 8.1))
    }

    @AfterEach
    fun tearDown() {
        repository.getMovies().deleteMany("{ title: {\$regex : \"Blade Runner.*\"} }")
    }

    @Test
    fun `should create client`() {
        repository.client `should be instance of` MongoClient::class
    }

    @Test
    fun `should retrieve database movies-app`() {
        repository.database `should be instance of` MongoDatabase::class
        repository.database.name `should be` "movies_app"
    }

    @Test
    fun `should retrieve collection Movies`() {
        repository.getMovies() `should be instance of` MongoCollection::class
        repository.getMovies().countDocuments() `should be` 1
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