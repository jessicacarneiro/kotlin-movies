package io.github.jessicacarneiro.moviesapp.application

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCursor
import io.github.jessicacarneiro.moviesapp.domain.Movie
import io.github.jessicacarneiro.moviesapp.infrastructure.KMongoRepository
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class MoviesServiceTest {

    private val mongoCursorMock = mock<MongoCursor<Movie>>()
    private val moviesIterableMock = mock<FindIterable<Movie>>()
    private val repositoryMock = mock<KMongoRepository>()

    private val service = MoviesService(repositoryMock)

    @Test
    fun `return empty list if no movie available`() {
        whenever(mongoCursorMock.hasNext()).thenReturn(false)
        whenever(moviesIterableMock.iterator()).thenReturn(mongoCursorMock)
        whenever(repositoryMock.getMovies()).thenReturn(moviesIterableMock)

        service.getAllMovies() `should be` emptyList()
        verify(repositoryMock).getMovies()
    }

    @Test
    fun `return list with Movies if there are movies available`() {
        whenever(mongoCursorMock.hasNext()).thenReturn(true, true, false)
        whenever(mongoCursorMock.next())
            .thenReturn(
                Movie("Eternal Sunshine of the Spotless Mind", 2004, 8.3),
                Movie("Scott Pilgrim vs. the World", 2010, 7.5)
            )
        whenever(moviesIterableMock.iterator()).thenReturn(mongoCursorMock)
        whenever(repositoryMock.getMovies()).thenReturn(moviesIterableMock)

        service.getAllMovies().count() `should be` 2
        verify(repositoryMock).getMovies()
    }
}
