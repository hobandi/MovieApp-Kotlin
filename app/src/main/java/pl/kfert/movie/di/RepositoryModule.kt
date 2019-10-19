package pl.kfert.movie.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import pl.kfert.movie.api.MovieRemoteDataSource
import pl.kfert.movie.data.dao.AppDatabase
import pl.kfert.movie.data.repository.*


val repositoryModule = module {
    single { createDatabaseName() }
    single { createAppDatabase(get(), get()) }
    single { createMovieDao(get()) }
    single { Gson() }
    single { CoroutineScope(Dispatchers.IO) }
    single { MovieRemoteDataSource(get()) }
    single <MainRepository> { MainRepositoryImpl(get())}
    single <MainListRepository> { MainListRepositoryImpl(get(), get())}
    single <DetailRepository> { DetailRepositoryImpl(get(), get()) }
}


fun createDatabaseName() = "DATABASE_NAME"

fun createAppDatabase(dbName: String, context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()

fun createMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
