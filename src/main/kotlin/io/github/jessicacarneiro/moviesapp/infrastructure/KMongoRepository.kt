package io.github.jessicacarneiro.moviesapp.infrastructure

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import io.github.jessicacarneiro.moviesapp.domain.Movie
import org.litote.kmongo.KMongo
import org.litote.kmongo.find
import org.litote.kmongo.getCollection
import org.springframework.stereotype.Repository

@Repository
class KMongoRepository {
    val client = KMongo.createClient()
    val database: MongoDatabase = client.getDatabase("movies_app")

    fun getMovies(): MongoCollection<Movie> {
        return database.getCollection()
    }

    fun findMovies(query: String): FindIterable<Movie> {
        return database.getCollection<Movie>().find(query)
    }
}