package eu.vmpay.jsonplaceholder.repository.remote

import eu.vmpay.jsonplaceholder.repository.Comment
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.repository.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPlaceholderService {

    @GET("posts")
    suspend fun getPosts(
        @Query("_limit") limit: Int?,
        @Query("_start") offset: Int?
    ): List<Post>

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User

    @GET("posts/{postId}/comments")
    suspend fun getPostComments(
        @Path("postId") postId: Int,
        @Query("_limit") limit: Int?,
        @Query("_start") offset: Int?
    ): List<Comment>
}