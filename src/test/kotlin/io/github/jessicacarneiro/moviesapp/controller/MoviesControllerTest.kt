package io.github.jessicacarneiro.moviesapp.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.jessicacarneiro.moviesapp.application.MoviesService
import io.github.jessicacarneiro.moviesapp.controller.input.MovieRequest
import io.github.jessicacarneiro.moviesapp.domain.Movie
import io.restassured.RestAssured.given
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.emptyString
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class MoviesControllerTest {

    @MockBean
    private lateinit var serviceMock: MoviesService

    private val mapper = jacksonObjectMapper()

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

    @Test
    fun `should return created when a movie is inserted`() {
        val newMovie = MovieRequest("Die Hard", 1988, 6.7)
        val body = mapper.writeValueAsString(newMovie)

        given()
            .contentType("application/json")
            .body(body)
            .post("/movies")
            .then()
            .statusCode(201)
    }
}
