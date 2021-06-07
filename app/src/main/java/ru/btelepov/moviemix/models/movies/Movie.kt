package ru.btelepov.moviemix.models.movies

import ru.btelepov.moviemix.models.genres.Genres


data class Movie(

    val adult: Boolean? = null,

    val backdropPath: String? = null,

    val genreIds: List<Int>? = null,
    val genres: List<Genres>? = null,

    val id: Int? = null,
    val originalLanguage: String? = null,

    val originalTitle: String? = null,

    val overview: String? = null,

    val popularity: Double? = null,

    val posterPath: String? = null,

    val releaseDate: String? = null,

    val title: String? = null,

    val video: Boolean? = null,

    val voteAverage: Double? = null,

    val voteCount: Int? = null


)
