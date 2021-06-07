package ru.btelepov.moviemix.data.database.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.btelepov.moviemix.models.movies.MovieResponse

@Entity(tableName = "movies_table")
class MovieEntity(
     var movieResponse: MovieResponse
) {


    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}