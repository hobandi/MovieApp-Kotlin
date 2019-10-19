package pl.kfert.movie.api

class MovieRemoteDataSource(private val api: ApiService) : BaseDataSource()  {

    suspend fun fetchMovies() = getResult {
        api.getNowPlayingAsync()
    }

    suspend fun fetchMovie(movieId : String) = getResult{ api.getMovieAsync(movieId) }

    suspend fun searchMovie(query : String) = getResult{ api.getSearchMovies(query) }

}