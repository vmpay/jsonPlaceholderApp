package eu.vmpay.jsonplaceholder.repository

import eu.vmpay.jsonplaceholder.repository.local.CommentsDao
import eu.vmpay.jsonplaceholder.repository.local.PostsDao
import eu.vmpay.jsonplaceholder.repository.local.UsersDao
import eu.vmpay.jsonplaceholder.repository.remote.JsonPlaceholderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val jsonPlaceholderService: JsonPlaceholderService,
    private val postsDao: PostsDao,
    private val commentsDao: CommentsDao,
    private val usersDao: UsersDao
) {

    fun getPostsListFactory() = postsDao.getFactory()

    suspend fun getPost(postId: Int): List<Post> = postsDao.getById(postId)

    suspend fun fetchPosts(limit: Int, offset: Int): List<Post> = withContext(Dispatchers.IO) {
        val data = jsonPlaceholderService.getPosts(limit, offset)
        postsDao.insert(data.toTypedArray())
        data
    }

    suspend fun getUser(userId: String) = usersDao.getById(userId)

    suspend fun fetchUser(userId: Int) = withContext(Dispatchers.IO) {
        val data = jsonPlaceholderService.getUser(userId)
        usersDao.insertOrUpdate(data)
        data
    }

    suspend fun getCommentsByPost(postId: Int) = commentsDao.getByPostId(postId)

    fun getCommentsByPostFactory(postId: Int) = commentsDao.getFactory(postId)

    suspend fun fetchCommentsByPost(postId: Int, limit: Int, offset: Int) =
        withContext(Dispatchers.IO) {
            val data = jsonPlaceholderService.getPostComments(postId, limit, offset)
            commentsDao.insertOrUpdate(data)
            data
        }
}