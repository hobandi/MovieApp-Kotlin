package pl.kfert.movie.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.kfert.movie.data.model.Movie
import pl.kfert.movie.ui.detail.DetailFragmentViewModel
import pl.kfert.movie.ui.mainlist.MainListFragmentViewModel
import pl.kfert.movie.ui.main.MainActivityViewModel

val viewModelModule = module {
    viewModel { MainListFragmentViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { (movie : Movie, save : Boolean) -> DetailFragmentViewModel(get(), movie, save) }
}