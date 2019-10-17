package pl.kfert.movie.data.repository

import pl.kfert.movie.api.MovieRemoteDataSource
import pl.kfert.movie.data.DataResult
import pl.kfert.movie.data.resultLiveData

class MainRepository(private val moveRemoteDataSource: MovieRemoteDataSource) {

    suspend fun getMoviesForSearch(query : String) = moveRemoteDataSource.searchMovie(query)

}