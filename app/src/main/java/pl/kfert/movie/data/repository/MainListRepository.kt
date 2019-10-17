package pl.kfert.movie.data.repository

import androidx.lifecycle.distinctUntilChanged
import pl.kfert.movie.api.MovieRemoteDataSource
import pl.kfert.movie.data.dao.MovieDao
import pl.kfert.movie.data.model.Movie
import pl.kfert.movie.data.base.resultLiveData

class MainListRepository(private val dao: MovieDao,
                         private val moveRemoteDataSource: MovieRemoteDataSource) {

     fun getMovies() = resultLiveData(
         databaseQuery = {
             dao.getMovieList()
         },
         networkCall = {
             moveRemoteDataSource.fetchMovies()
         },
         saveCallResult = { api, oldData ->
             oldData?.forEach { item ->
                 api.results?.firstOrNull { item.id == it.id }
                     ?.apply { isFavorite = item.isFavorite }
             }
             api.results?.let { dao.insert(it) }
         }).distinctUntilChanged()

     suspend fun updateDB(movie : Movie)
     {
        dao.update(movie)
     }

}