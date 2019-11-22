package eu.vmpay.jsonplaceholder.repository.local

import androidx.room.Dao
import androidx.room.Query
import eu.vmpay.jsonplaceholder.repository.User

@Dao
abstract class UsersDao : BaseDao<User>() {

    @Query("SELECT * FROM users")
    abstract suspend fun get(): List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    abstract suspend fun getById(id: String): List<User>
}