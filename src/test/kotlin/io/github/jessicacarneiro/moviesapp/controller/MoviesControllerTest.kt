package io.github.jessicacarneiro.moviesapp.controller

import io.github.jessicacarneiro.moviesapp.application.MoviesService
import io.github.jessicacarneiro.moviesapp.domain.Movie
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
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
        }
    }
}