package pl.kfert.movie.data.repository

import androidx.lifecycle.distinctUntilChanged
import pl.kfert.movie.api.MovieRemoteDataSource
import pl.kfert.movie.data.base.resultLiveData
import pl.kfert.movie.data.dao.MovieDao
import pl.kfert.movie.data.model.Movie

class DetailRepositoryImpl(private val dao: MovieDao,
                           private val moveRemoteDataSource: MovieRemoteDataSource) : DetailRepository {

    override fun getMovie(movieId: String, save: Boolean) = resultLiveData(
        databaseQuery = {
            dao.getMovie(movieId)
        },
        networkCall = {
            moveRemoteDataSource.fetchMovie(movieId)
        }, beforeInsert = { oldEntry, api ->
            oldEntry?.apply { api.data!!.isFavorite = isFavorite }
            api.data!!
        }, insertQuery = {
            if (save) dao.update(it)
        }
    ).distinctUntilChanged()

    override suspend fun updateDB(movie : Movie) {
        dao.update(movie)
    }

}
