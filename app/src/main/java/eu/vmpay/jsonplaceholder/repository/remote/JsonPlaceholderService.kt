package eu.vmpay.jsonplaceholder.repository.remote

import eu.vmpay.jsonplaceholder.repository.Comment
import eu.vmpay.jsonplaceholder.repository.Post
import eu.vmpay.jsonplaceholder.repository.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderService {

    @GET("posts")
    fun getPosts(): Single<List<Post>>

    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<User>

    @GET("posts/{postId}/comments")
    fun getPostComments(@Path("postId") postId: Int): Single<List<Comment>>
}