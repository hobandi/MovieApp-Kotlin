package pl.kfert.movie.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.kfert.movie.data.model.Movie
import pl.kfert.movie.ui.detail.DetailViewModel
import pl.kfert.movie.ui.mainlist.MainListViewModel
import pl.kfert.movie.ui.main.MainViewModel

val viewModelModule = module {
    viewModel { MainListViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { (movie : Movie) -> DetailViewModel(get(), movie) }
}