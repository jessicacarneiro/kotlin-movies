package io.github.jessicacarneiro.moviesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MoviesAppApplication

fun main(args: Array<String>) {
    runApplication<MoviesAppApplication>(*args)
}
