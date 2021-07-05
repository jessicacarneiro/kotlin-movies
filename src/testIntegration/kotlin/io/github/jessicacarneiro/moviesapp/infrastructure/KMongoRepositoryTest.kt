package io.github.jessicacarneiro.moviesapp.infrastructure

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import io.github.jessicacarneiro.moviesapp.domain.Movie
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
        repository.getMovies().deleteMany("{ title: 'Blade Runner' }")
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
}

