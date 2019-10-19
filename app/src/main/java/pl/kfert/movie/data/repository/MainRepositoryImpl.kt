package pl.kfert.movie.data.repository

import pl.kfert.movie.api.MovieRemoteDataSource

class MainRepositoryImpl(private val moveRemoteDataSource: MovieRemoteDataSource) : MainRepository {

    override suspend fun getMoviesForSearch(query : String) = moveRemoteDataSource.searchMovie(query)

}