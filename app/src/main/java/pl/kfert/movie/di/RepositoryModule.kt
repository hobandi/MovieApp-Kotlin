package pl.kfert.movie.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import pl.kfert.movie.api.MovieRemoteDataSource
import pl.kfert.movie.data.dao.AppDatabase
import pl.kfert.movie.data.repository.DetailRepository
import pl.kfert.movie.data.repository.MainListRepository
import pl.kfert.movie.data.repository.MainRepository


val repositoryModule = module {
    single { createDatabaseName() }
    single { createAppDatabase(get(), get()) }
    single { createMovieDao(get()) }
    single { Gson() }
    single { CoroutineScope(Dispatchers.IO) }
    single { MovieRemoteDataSource(get()) }
    single { MainListRepository(get(), get())}
    single { MainRepository(get())}
    single { DetailRepository(get(), get()) }
}


fun createDatabaseName() = "DATABASE_NAME"

fun createAppDatabase(dbName: String, context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()

fun createMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
