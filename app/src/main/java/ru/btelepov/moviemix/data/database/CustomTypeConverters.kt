package ru.btelepov.moviemix.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.btelepov.moviemix.models.movies.MovieData
import ru.btelepov.moviemix.models.movies.MovieResponse
import ru.btelepov.moviemix.models.serials.SerialItem
import ru.btelepov.moviemix.models.serials.SerialResponse

class CustomTypeConverters {

    var gson = Gson()

    @TypeConverter
    fun movieItemsToString(movieResponse: MovieResponse): String {
        return gson.toJson(movieResponse)
    }

    @TypeConverter
    fun stringToMovieResponse(data: String): MovieResponse {
        val listType = object : TypeToken<MovieResponse>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun serialItemsToString(serialResponse: SerialResponse): String {
        return gson.toJson(serialResponse)
    }

    @TypeConverter
    fun stringToSerialResponse(data: String): SerialResponse {
        val listType = object : TypeToken<SerialResponse>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun movieToString(movieData: MovieData): String {
        return gson.toJson(movieData)
    }

    @TypeConverter
    fun stringToMovie(data: String): MovieData {
        val listType = object : TypeToken<MovieData>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun serialToString(serial: SerialItem): String {
        return gson.toJson(serial)
    }

    @TypeConverter
    fun stringToSerial(data: String): SerialItem {
        val listType = object : TypeToken<SerialItem>() {}.type
        return gson.fromJson(data, listType)
    }
}