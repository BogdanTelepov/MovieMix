package ru.btelepov.moviemix.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val locale = localDataSource
    val remote = remoteDataSource
}