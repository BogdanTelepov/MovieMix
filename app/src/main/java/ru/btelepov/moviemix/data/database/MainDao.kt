package ru.btelepov.moviemix.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.btelepov.moviemix.data.database.enteties.*

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieItem(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovieItem(popularMovieEntity: PopularMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRatedMovieItem(topRatedMovieEntity: TopRatedMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerialItem(serialEntity: SerialEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovieItem(favoriteMovieEntity: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteSerialItem(favoriteSerialEntity: FavoriteSerialEntity)

    @Query("SELECT * FROM movies_table ORDER BY id ASC")
    fun readMovieItems(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM popular_movies ORDER BY id ASC")
    fun readPopularMovies(): Flow<List<PopularMovieEntity>>

    @Query("SELECT * FROM top_rated_movies ORDER BY id ASC")
    fun readTopRatedMovies(): Flow<List<TopRatedMovieEntity>>

    @Query("SELECT * FROM serials_table ORDER BY id ASC")
    fun readSerialItems(): Flow<List<SerialEntity>>

    @Query("SELECT * FROM favorites_movie_table ORDER BY id ASC")
    fun readFavoriteMovies(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT * FROM favorites_serial_table ORDER BY id ASC")
    fun readFavoriteSerials(): Flow<List<FavoriteSerialEntity>>


    @Delete
    suspend fun deleteFavoriteMovieItem(favoriteMovieEntity: FavoriteMovieEntity)

    @Delete
    suspend fun deleteFavoriteSerialItem(favoriteSerialEntity: FavoriteSerialEntity)


    @Query("DELETE FROM favorites_movie_table")
    suspend fun deleteAllFavoriteMovieItems()

    @Query("DELETE FROM favorites_serial_table")
    suspend fun deleteAllFavoriteSerialItems()
}