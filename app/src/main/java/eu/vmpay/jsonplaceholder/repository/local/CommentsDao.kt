package eu.vmpay.jsonplaceholder.repository.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import eu.vmpay.jsonplaceholder.repository.Comment

@Dao
abstract class CommentsDao : BaseDao<Comment>() {

    @Query("SELECT * FROM comments")
    abstract suspend fun get(): List<Comment>

    @Query("SELECT * FROM comments WHERE postId = :postId")
    abstract suspend fun getByPostId(postId: Int): List<Comment>

    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY id DESC")
    abstract fun getFactory(postId: Int): DataSource.Factory<Int, Comment>
}