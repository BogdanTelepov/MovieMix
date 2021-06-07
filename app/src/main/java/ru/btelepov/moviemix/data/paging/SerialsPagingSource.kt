package ru.btelepov.moviemix.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.btelepov.moviemix.data.network.SerialApi
import ru.btelepov.moviemix.models.serials.SerialItem
import java.io.IOException


class SerialsPagingSource(private val serialApi: SerialApi) : PagingSource<Int, SerialItem>() {

    override fun getRefreshKey(state: PagingState<Int, SerialItem>): Int {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SerialItem> {
        val position = params.key ?: STARTING_PAGE_INDEX


        return try {
            val response = serialApi.getTvPopular(page = position)
            val serials = response.body()!!.serialItems
            LoadResult.Page(
                data = serials,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (serials.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }

    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}