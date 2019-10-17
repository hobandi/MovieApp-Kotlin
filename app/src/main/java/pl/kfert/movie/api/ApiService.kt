package pl.kfert.movie.api


import pl.kfert.movie.data.GetMovieListResponse
import pl.kfert.movie.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingAsync(): Response<GetMovieListResponse>

    @GET("3/search/movie")
    suspend fun getSearchMovies(@Query("query") query : String): Response<GetMovieListResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieAsync(@Path("movie_id") movieId: String): Response<Movie>

}