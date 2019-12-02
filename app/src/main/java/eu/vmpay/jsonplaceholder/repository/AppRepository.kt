package eu.vmpay.jsonplaceholder.repository

import eu.vmpay.jsonplaceholder.repository.local.CommentsDao
import eu.vmpay.jsonplaceholder.repository.local.PostsDao
import eu.vmpay.jsonplaceholder.repository.local.UsersDao
import eu.vmpay.jsonplaceholder.repository.remote.JsonPlaceholderService
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val jsonPlaceholderService: JsonPlaceholderService,
    private val postsDao: PostsDao,
    private val commentsDao: CommentsDao,
    private val usersDao: UsersDao
) {

    fun getPostsList() = postsDao.get()

    fun getPostsListFactory() = postsDao.getFactory()

    fun getPost(postId: Int) = postsDao.getById(postId)

    // TODO() Paging library integration is coming soon
    fun fetchPosts(limit: Int?, offset: Int?) = jsonPlaceholderService.getPosts(limit, offset)
        .doOnSuccess { postsDao.insertOrUpdate(it).subscribe() }

    fun getUser(userId: String) = usersDao.getById(userId)

    fun fetchUser(userId: Int) = jsonPlaceholderService.getUser(userId)
        .doOnSuccess { usersDao.insertOrUpdate(it).subscribe() }

    fun getCommentsByPost(postId: Int) = commentsDao.getByPostId(postId)

    fun getCommentsByPostFactory(postId: Int) = commentsDao.getFactory(postId)

    // TODO() Paging library integration is coming soon
    fun fetchCommentsByPost(postId: Int, limit: Int?, offset: Int?) =
        jsonPlaceholderService.getPostComments(postId, limit, offset)
        .doOnSuccess { commentsDao.insertOrUpdate(it).subscribe() }
}