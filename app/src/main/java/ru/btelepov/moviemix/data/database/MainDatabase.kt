package ru.btelepov.moviemix.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.btelepov.moviemix.data.database.enteties.*

@Database(
    entities = [MovieEntity::class, SerialEntity::class, FavoriteMovieEntity::class, FavoriteSerialEntity::class, PopularMovieEntity::class, TopRatedMovieEntity::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(CustomTypeConverters::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun mainDao(): MainDao
}