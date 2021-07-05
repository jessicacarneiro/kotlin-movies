package io.github.jessicacarneiro.moviesapp.controller

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MoviesControllerTest {

    @Test
    fun `should return empty array if no movies are available`() {
        Given {
            port(8080)
        }
        When {
            get("/movies")
        } Then {
            statusCode(200)
            body("isEmpty()", Matchers.`is`(true))
        }
    }
}