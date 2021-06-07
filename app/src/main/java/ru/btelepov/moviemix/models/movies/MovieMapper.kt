package ru.btelepov.moviemix.models.movies

import ru.btelepov.moviemix.data.DataMapper
import ru.btelepov.moviemix.models.genres.GenreData
import ru.btelepov.moviemix.models.genres.Genres

class MovieMapper : DataMapper<MovieData, Movie> {

    override fun fromApiData(model: MovieData): Movie = with(model) {
        return Movie(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            genres = genres?.map { Genres(it.id, it.name) },
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount

        )
    }

    override fun toApiData(model: Movie): MovieData = with(model) {
        return MovieData(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            genres = genres?.map { GenreData(it.id, it.name) },
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}