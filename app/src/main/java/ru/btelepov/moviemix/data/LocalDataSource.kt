package ru.btelepov.moviemix.data

import kotlinx.coroutines.flow.Flow
import ru.btelepov.moviemix.data.database.MainDao
import ru.btelepov.moviemix.data.database.enteties.*
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mainDao: MainDao) {


    fun readMovieItems(): Flow<List<MovieEntity>> {
        return mainDao.readMovieItems()
    }

    fun readPopularMovies(): Flow<List<PopularMovieEntity>> {
        return mainDao.readPopularMovies()
    }

    fun readTopRatedMovies(): Flow<List<TopRatedMovieEntity>> {
        return mainDao.readTopRatedMovies()
    }

    fun readSerialItems(): Flow<List<SerialEntity>> {
        return mainDao.readSerialItems()
    }

    fun readFavoriteMovies(): Flow<List<FavoriteMovieEntity>> {
        return mainDao.readFavoriteMovies()
    }

    fun readFavoriteSerials(): Flow<List<FavoriteSerialEntity>> {
        return mainDao.readFavoriteSerials()
    }

    suspend fun insertPopularMovieItem(popularMovieEntity: PopularMovieEntity) {
        mainDao.insertPopularMovieItem(popularMovieEntity)

    }

    suspend fun insertTopRatedMovieItem(topRatedMovieEntity: TopRatedMovieEntity) {
        mainDao.insertTopRatedMovieItem(topRatedMovieEntity)
    }

    suspend fun insertMovieItem(movieEntity: MovieEntity) {
        mainDao.insertMovieItem(movieEntity)
    }

    suspend fun insertSerialItem(serialEntity: SerialEntity) {
        mainDao.insertSerialItem(serialEntity)
    }

    suspend fun insertFavoriteMovieItem(favoriteMovieEntity: FavoriteMovieEntity) {
        mainDao.insertFavoriteMovieItem(favoriteMovieEntity)
    }

    suspend fun insertFavoriteSerialItem(favoriteSerialEntity: FavoriteSerialEntity) {
        mainDao.insertFavoriteSerialItem(favoriteSerialEntity)
    }

    suspend fun deleteFavoriteMovieItem(favoriteMovieEntity: FavoriteMovieEntity) {
        mainDao.deleteFavoriteMovieItem(favoriteMovieEntity)
    }

    suspend fun deleteFavoriteSerialItem(favoriteSerialEntity: FavoriteSerialEntity) {
        mainDao.deleteFavoriteSerialItem(favoriteSerialEntity)
    }

    suspend fun deleteAllFavoritesMovies() {
        mainDao.deleteAllFavoriteMovieItems()
    }

    suspend fun deleteAllFavoritesSerials() {
        mainDao.deleteAllFavoriteSerialItems()
    }


}