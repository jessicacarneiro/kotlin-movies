package io.github.jessicacarneiro.moviesapp.infrastructure

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.InsertOneResult
import io.github.jessicacarneiro.moviesapp.configuration.MongoDBProperties
import io.github.jessicacarneiro.moviesapp.domain.Movie
import org.litote.kmongo.KMongo
import org.litote.kmongo.find
import org.litote.kmongo.getCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class KMongoRepository @Autowired constructor(mongoDBProperties: MongoDBProperties) {

    val client = KMongo.createClient()
    val database: MongoDatabase = client.getDatabase(mongoDBProperties.database.name)

    fun getMovies(): MongoCollection<Movie> {
        return database.getCollection()
    }

    fun findMovies(query: String): FindIterable<Movie> {
        return database.getCollection<Movie>().find(query)
    }

    fun insertMovie(movie: Movie): InsertOneResult {
        return database.getCollection<Movie>().insertOne(movie)
    }
}