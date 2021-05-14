package ru.btelepov.moviemix.data.database.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.btelepov.moviemix.models.Movie
import ru.btelepov.moviemix.models.MovieResponse

@Entity(tableName = "movies_table")
class MovieEntity(
     var movieResponse: MovieResponse
) {


    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}