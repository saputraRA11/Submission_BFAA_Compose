package com.example.githubusernavigationdanapi.data.remote.retrofit
import com.example.githubusernavigationdanapi.data.remote.response.DetailUserResponse
import com.example.githubusernavigationdanapi.data.remote.response.ListFollowerResponseItem
import com.example.githubusernavigationdanapi.data.remote.response.ListFollowingResponseItem
import com.example.githubusernavigationdanapi.data.remote.response.SearchUserResponse
import retrofit2.http.*

interface ApiService {
    @Headers(
        "User-Agent: request"
    )
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String
    ): SearchUserResponse

    @Headers(
        "User-Agent: request"
    )
    @GET("users/{username}")
    suspend fun detailUser(
        @Path("username") username: String
    ): DetailUserResponse

    @Headers(
        "User-Agent: request"
    )
    @GET("users/{username}/followers")
    suspend fun followersUser(
        @Path("username") username: String
    ): List<ListFollowerResponseItem>

    @Headers(
        "User-Agent: request"
    )
    @GET("users/{username}/following")
    suspend fun followingUser(
        @Path("username") username: String
    ): List<ListFollowingResponseItem>
}