package ru.btelepov.moviemix.data.database.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.btelepov.moviemix.models.movies.MovieData

@Entity(tableName = "favorites_movie_table")
class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var movieDataItem: MovieData
) {
}