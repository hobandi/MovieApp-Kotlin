package pl.kfert.movie.data.repository

import pl.kfert.movie.data.DataResult
import pl.kfert.movie.data.GetMovieListResponse


interface MainRepository {

    suspend fun getMoviesForSearch(query : String) : DataResult<GetMovieListResponse>

}