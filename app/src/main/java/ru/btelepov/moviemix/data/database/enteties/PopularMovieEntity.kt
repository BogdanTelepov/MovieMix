package ru.btelepov.moviemix.data.database.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.btelepov.moviemix.models.MovieResponse

@Entity(tableName = "popular_movies")
class PopularMovieEntity(
    var movieResponse: MovieResponse
) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}