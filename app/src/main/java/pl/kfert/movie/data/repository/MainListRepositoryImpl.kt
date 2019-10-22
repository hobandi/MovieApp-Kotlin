package pl.kfert.movie.data.repository

import androidx.lifecycle.distinctUntilChanged
import pl.kfert.movie.api.MovieRemoteDataSource
import pl.kfert.movie.data.base.resultLiveData
import pl.kfert.movie.data.dao.MovieDao
import pl.kfert.movie.data.model.Movie

class MainListRepositoryImpl(
    private val dao: MovieDao,
    private val moveRemoteDataSource: MovieRemoteDataSource
) : MainListRepository {

    override fun getMovies() = resultLiveData(
        databaseQuery = {
            dao.getMovieList()
        },
        networkCall = {
            moveRemoteDataSource.fetchMovies()
        }, beforeInsert = { oldEntry, api ->
            oldEntry?.map { item ->
                api.data?.results?.firstOrNull{ item.id == it.id }?.apply {
                    isFavorite = item.isFavorite
                }
            }
            api.data?.results!!.sortedBy { it.id }
        }, insertQuery = {
            dao.insert(it)
        }
    ).distinctUntilChanged()

    override suspend fun updateDB(movie: Movie) {
        dao.update(movie)
    }

}