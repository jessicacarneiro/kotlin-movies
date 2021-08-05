package io.github.jessicacarneiro.moviesapp.infrastructure

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import io.github.jessicacarneiro.moviesapp.configuration.MongoDBProperties
import io.github.jessicacarneiro.moviesapp.domain.Movie
import org.litote.kmongo.KMongo
import org.litote.kmongo.deleteMany
import org.litote.kmongo.find
import org.litote.kmongo.getCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class KMongoRepository @Autowired constructor(mongoDBProperties: MongoDBProperties) {

    private val client = KMongo.createClient()
    private val database: MongoDatabase = client.getDatabase(mongoDBProperties.database.name)

    private fun getMoviesCollection(): MongoCollection<Movie> {
        return database.getCollection<Movie>()
    }

    fun getMovies(): FindIterable<Movie> {
        return getMoviesCollection().find()
    }

    fun findMovies(query: String): FindIterable<Movie> {
        return getMoviesCollection().find(query)
    }

    fun insertMovie(movie: Movie): InsertOneResult {
        return getMoviesCollection().insertOne(movie)
    }

    fun deleteMovies(filter: String): DeleteResult {
        return getMoviesCollection().deleteMany(filter)
    }
}
