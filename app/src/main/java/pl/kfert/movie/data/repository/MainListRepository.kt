package pl.kfert.movie.data.repository

import androidx.lifecycle.LiveData
import pl.kfert.movie.data.DataResult
import pl.kfert.movie.data.model.Movie


interface MainListRepository {

    fun getMovies() : LiveData<DataResult<List<Movie>>>

    suspend fun updateDB(movie : Movie)

}