package pl.kfert.movie.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kfert.movie.data.model.Movie
import pl.kfert.movie.data.repository.DetailRepository

class DetailViewModel(private val detailRepository: DetailRepository, private val listMovie : Movie) : ViewModel(){

    val movie by lazy { detailRepository.getMovie(listMovie.id) }

    val favoriteChanged = MutableLiveData<Boolean>()

    init {
        listMovie.isFavorite.let { favoriteChanged.postValue(it) }
    }

    private fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                detailRepository.updateDB(movie)
                favoriteChanged.postValue(movie.isFavorite)
            } catch (e: Exception) {
                Log.e(DetailViewModel::class.simpleName, "${e.message}")
            }
        }
    }

    fun setFavorite(isFavorite : Boolean)
    {
        listMovie.isFavorite = isFavorite
        updateMovie(listMovie)
    }

}
