package io.github.jessicacarneiro.moviesapp.infrastructure

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

internal class KMongoRepositoryTest {

    private val repository = KMongoRepository()

    @Test
    internal fun `should create client`() {
        repository.client `should be instance of` MongoClient::class
    }

    @Test
    internal fun `should retrieve database movies-app`() {
        repository.database `should be instance of` MongoDatabase::class
        repository.database.name `should be` "movies-app"
    }
}