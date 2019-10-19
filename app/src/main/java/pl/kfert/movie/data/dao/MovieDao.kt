package pl.kfert.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import pl.kfert.movie.data.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getMovieList(): List<Movie>

    @Query("SELECT * FROM movie WHERE movie.id = :id")
    fun getMovie(id: String): Movie

    @Insert(onConflict = REPLACE)
    suspend fun insert(list: List<Movie>)

    @Insert(onConflict = REPLACE)
    suspend fun update(movie: Movie)

}