package pl.kfert.movie.data.repository

import pl.kfert.movie.api.MovieRemoteDataSource

class MainRepository(private val moveRemoteDataSource: MovieRemoteDataSource) {

    suspend fun getMoviesForSearch(query : String) = moveRemoteDataSource.searchMovie(query)

}