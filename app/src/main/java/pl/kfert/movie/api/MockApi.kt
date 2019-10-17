package pl.kfert.movie.api

import pl.kfert.movie.data.GetMovieListResponse
import pl.kfert.movie.data.model.Movie
import retrofit2.Response

class MockApi(
) : ApiService {
    override suspend fun getSearchMovies(query: String): Response<GetMovieListResponse> {
        TODO("not implemented")
    }

    override suspend fun getNowPlayingAsync(): Response<GetMovieListResponse> {
        TODO("not implemented")
    }

    override suspend fun getMovieAsync(movieId: String): Response<Movie> {
        TODO("not implemented")
    }

}