package io.github.jessicacarneiro.moviesapp.controller

import io.github.jessicacarneiro.moviesapp.application.MoviesService
import io.github.jessicacarneiro.moviesapp.domain.Movie
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.emptyString
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MoviesControllerTest {

    @MockBean
    private lateinit var serviceMock: MoviesService

    @Test
    fun `should return status no content and empty array if no movies are available`() {
        whenever(serviceMock.getAllMovies()).thenReturn(emptyList())

        Given {
            port(8080)
        }
        When {
            get("/movies")
        } Then {
            statusCode(204)
            body(emptyString())
        }
    }

    @Test
    fun `should return ok and array if there are movies available`() {
        whenever(serviceMock.getAllMovies()).thenReturn(listOf(Movie("3 Idiots", 2009, 8.4)))

      Given {
            port(8080)
        }
        When {
            get("/movies")
        } Then {
            statusCode(200)
            body("size", `is`(1))
            body("[0].title", `is`("3 Idiots"))
            body("[0].year", `is`(2009))
            body("[0].score", `is`((8.4).toFloat()))
        }
    }
}