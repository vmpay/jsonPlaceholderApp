package eu.vmpay.jsonplaceholder.repository.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import eu.vmpay.jsonplaceholder.repository.Comment
import io.reactivex.Flowable

@Dao
abstract class CommentsDao : BaseDao<Comment>() {

    @Query("SELECT * FROM comments")
    abstract fun get(): Flowable<List<Comment>>

    @Query("SELECT * FROM comments WHERE postId = :postId")
    abstract fun getByPostId(postId: Int): Flowable<List<Comment>>

    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY id DESC")
    abstract fun getFactory(postId: Int): DataSource.Factory<Int, Comment>
}