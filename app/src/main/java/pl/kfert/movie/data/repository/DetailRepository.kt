package pl.kfert.movie.data.repository

import androidx.lifecycle.distinctUntilChanged
import pl.kfert.movie.api.MovieRemoteDataSource
import pl.kfert.movie.data.dao.MovieDao
import pl.kfert.movie.data.model.Movie
import pl.kfert.movie.data.resultLiveData

class DetailRepository(private val dao: MovieDao,
                         private val moveRemoteDataSource: MovieRemoteDataSource) {

    fun getMovie(movieId : String) = resultLiveData(
        databaseQuery = {
           dao.getMovie(movieId)
        },
        networkCall = {
            moveRemoteDataSource.fetchMovie(movieId) },
        saveCallResult = { api , oldData ->
            oldData?.let { api.isFavorite = oldData.isFavorite }
            api?.let { dao.update(it) }
        }
    ).distinctUntilChanged()

    suspend fun updateDB(movie : Movie)
    {
        dao.update(movie)
    }

}
