package eu.vmpay.jsonplaceholder.repository.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import eu.vmpay.jsonplaceholder.repository.Post

@Dao
abstract class PostsDao : BaseDao<Post>() {

    @Query("SELECT * FROM posts ORDER BY id DESC")
    abstract fun getFactory(): DataSource.Factory<Int, Post>

    @Query("SELECT * FROM posts")
    abstract suspend fun get(): List<Post>

    @Query("SELECT * FROM posts WHERE id = :id")
    abstract suspend fun getById(id: Int): List<Post>
}