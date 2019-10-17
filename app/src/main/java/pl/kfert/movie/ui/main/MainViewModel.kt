package pl.kfert.movie.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kfert.movie.data.DataResult
import pl.kfert.movie.data.model.Movie
import pl.kfert.movie.data.repository.MainRepository


class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    var querySearch = MutableLiveData<List<String>>()

    var queryMovies : MutableList<Movie>? = arrayListOf()

    fun getListSearchForQuery(query : String) {
        viewModelScope.launch {
            try {
                val responseStatus = mainRepository.getMoviesForSearch(query)

                when {
                    responseStatus.status == DataResult.Status.SUCCESS -> {
                        querySearch.postValue(responseStatus.data?.results?.map { item -> item.title!! }?.toList())
                        queryMovies = responseStatus.data?.results?.toMutableList()
                    }
                    responseStatus.status == DataResult.Status.ERROR -> Log.d("MainViewModel","error")
                    responseStatus.status == DataResult.Status.LOADING -> Log.d("MainViewModel","loading")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel","exception $e")
            }
        }
    }

    fun getMovieId(title : String) : Movie? = queryMovies?.first { it.title == title }

}