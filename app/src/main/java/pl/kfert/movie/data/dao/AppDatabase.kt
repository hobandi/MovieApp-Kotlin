package pl.kfert.movie.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.kfert.movie.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}