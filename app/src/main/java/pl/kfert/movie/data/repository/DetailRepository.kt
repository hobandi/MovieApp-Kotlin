package pl.kfert.movie.data.repository

import androidx.lifecycle.LiveData
import pl.kfert.movie.data.DataResult
import pl.kfert.movie.data.model.Movie


interface DetailRepository {

        fun getMovie(movieId : String, save : Boolean) : LiveData<DataResult<Movie>>

        suspend fun updateDB(movie : Movie)

}