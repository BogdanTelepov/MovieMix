package ru.btelepov.moviemix.data

interface DataMapper<N, M> {

    fun fromApiData(model: N): M

    fun toApiData(model: M): N
}