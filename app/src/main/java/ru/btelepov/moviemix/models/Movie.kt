package ru.btelepov.moviemix.models

import com.google.gson.annotations.SerializedName

class Movie(
    posterPath: String,
    @SerializedName("adult")
    var isAdult: Boolean,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    var genreIds: List<Int>,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("title")
    var title: String?,

    backdropPath: String,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("vote_count")
    var voteCount: Int?,
    @SerializedName("video")
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Double
) {

    @SerializedName("backdrop_path")
    var backdropPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"

    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"

    val rating: Float
        get() = voteAverage.div(2).toFloat()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (isAdult != other.isAdult) return false
        if (overview != other.overview) return false
        if (releaseDate != other.releaseDate) return false
        if (id != other.id) return false
        if (originalTitle != other.originalTitle) return false
        if (originalLanguage != other.originalLanguage) return false
        if (title != other.title) return false
        if (backdropPath != other.backdropPath) return false
        if (popularity != other.popularity) return false
        if (voteCount != other.voteCount) return false
        if (video != other.video) return false
        if (voteAverage != other.voteAverage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isAdult.hashCode()
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + (id ?: 0)
        result = 31 * result + (originalTitle?.hashCode() ?: 0)
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + (popularity?.hashCode() ?: 0)
        result = 31 * result + (voteCount ?: 0)
        result = 31 * result + (video?.hashCode() ?: 0)
        result = 31 * result + voteAverage.hashCode()
        return result
    }
}
