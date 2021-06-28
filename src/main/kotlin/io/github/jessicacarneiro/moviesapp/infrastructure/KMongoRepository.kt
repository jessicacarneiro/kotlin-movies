package io.github.jessicacarneiro.moviesapp.infrastructure

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.springframework.stereotype.Repository

@Repository
class KMongoRepository {
    val client = KMongo.createClient()
    val database: MongoDatabase = client.getDatabase("movies-app")
}