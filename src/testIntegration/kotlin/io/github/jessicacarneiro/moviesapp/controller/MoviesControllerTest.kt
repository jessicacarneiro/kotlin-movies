package io.github.jessicacarneiro.moviesapp.controller

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCursor
import io.github.jessicacarneiro.moviesapp.domain.Movie
import io.github.jessicacarneiro.moviesapp.infrastructure.KMongoRepository
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MoviesControllerTest {

    @MockBean
    private lateinit var repositoryMock: KMongoRepository

    private val mongoCursorMock = mock<MongoCursor<Movie>>()
    private val moviesIterableMock = mock<FindIterable<Movie>>()

    @Test
    fun `should return status no content and empty array if no movies are available`() {
        whenever(mongoCursorMock.hasNext()).thenReturn(false)
        whenever(moviesIterableMock.iterator()).thenReturn(mongoCursorMock)
        whenever(repositoryMock.getMovies()).thenReturn(moviesIterableMock)

        Given {
            port(8080)
        }
        When {
            get("/movies")
        } Then {
            statusCode(204)
            body(Matchers.emptyString())
        }
    }

    @Test
    fun `should return ok and array if there are movies available`() {
        whenever(mongoCursorMock.hasNext()).thenReturn(true, true, false)
        whenever(mongoCursorMock.next())
            .thenReturn(
                Movie("Pretty Woman", 1990, 7.0),
                Movie("Gremlins", 1984, 7.3)
            )
        whenever(moviesIterableMock.iterator()).thenReturn(mongoCursorMock)
        whenever(repositoryMock.getMovies()).thenReturn(moviesIterableMock)

        Given {
            port(8080)
        }
        When {
            get("/movies")
        } Then {
            statusCode(200)
            body("size", Matchers.`is`(2))
            body("[0].title", Matchers.`is`("Pretty Woman"))
            body("[0].year", Matchers.`is`(1990))
            body("[0].score", Matchers.`is`((7.0).toFloat()))
            body("[1].title", Matchers.`is`("Gremlins"))
            body("[1].year", Matchers.`is`(1984))
            body("[1].score", Matchers.`is`((7.3).toFloat()))
        }
    }
}