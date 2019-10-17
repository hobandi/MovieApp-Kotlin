package pl.kfert.movie.ui.mainlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kfert.movie.data.model.Movie
import pl.kfert.movie.data.repository.MainListRepository

class MainListViewModel(private val repository: MainListRepository) : ViewModel() {

    val movies by lazy {
        repository.getMovies()
    }

    fun updateMovie(movie: Movie)
    {
        viewModelScope.launch {
            try {
                repository.updateDB(movie)
            } catch (e: Exception) {
                Log.e(MainListViewModel::class.simpleName,"${e.message}")
            }
        }
    }

}
