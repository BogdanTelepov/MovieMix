package ru.btelepov.moviemix.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.btelepov.moviemix.models.auth.*

interface AuthorizationApi {

    @GET("3/authentication/token/new")
    suspend fun createRequestToken(): Response<RequestTokenResponse>

    @POST("3/authentication/token/validate_with_login")
    suspend fun createSessionWithLogin(@Body requestLoginBody: RequestLoginBody): Response<SessionWithLoginResponse>

    @POST("3/authentication/session/new")
    suspend fun createSession(@Body sessionId: SessionId) : Response<NewSessionResponse>
}