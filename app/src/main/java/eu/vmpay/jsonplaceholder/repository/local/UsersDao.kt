package eu.vmpay.jsonplaceholder.repository.local

import androidx.room.Dao
import androidx.room.Query
import eu.vmpay.jsonplaceholder.repository.User
import io.reactivex.Flowable

@Dao
abstract class UsersDao : BaseDao<User>() {

    @Query("SELECT * FROM users")
    abstract fun get(): Flowable<List<User>>

    @Query("SELECT * FROM users WHERE id = :id")
    abstract fun getById(id: String): Flowable<List<User>>
}