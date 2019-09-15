package eu.vmpay.jsonplaceholder.repository.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import eu.vmpay.jsonplaceholder.repository.Post
import io.reactivex.Flowable

@Dao
abstract class PostsDao : BaseDao<Post>() {

    @Query("SELECT * FROM posts")
    abstract fun get(): Flowable<List<Post>>

    @Query("SELECT * FROM posts WHERE id = :id")
    abstract fun getById(id: Int): Flowable<List<Post>>

    @Query("SELECT * FROM posts ORDER BY id DESC")
    abstract fun getFactory(): DataSource.Factory<Int, Post>
}