package io.github.jessicacarneiro.moviesapp.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "mongodb")
data class MongoDBProperties(
    val database: DatabaseProperties
)

data class DatabaseProperties(
    val name: String
)
