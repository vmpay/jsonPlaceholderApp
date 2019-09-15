package eu.vmpay.jsonplaceholder.repository.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single

abstract class BaseDao<T> {

    /**
     * Insert an object in the database
     *
     * @param obj the object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(obj: T): Single<Long>

    /**
     * Insert an array of objects in the database. If there is the row with the same primary key,
     * this object will be replaced
     *
     * @param obj the objects to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(obj: Array<T>): Single<List<Long>>

    /**
     * Update an object from the database in a synchronous way
     *
     * @param obj the object to be updated
     */
    @Update
    abstract fun update(obj: T): Single<Int>

    /**
     * Update an object from the database in a synchronous way
     *
     * @param obj the object to be updated
     */
    @Update
    abstract fun updateSync(obj: T): Int

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    abstract fun delete(obj: T): Single<Int>

    /**
     * Insert an object in the database. Ignores if there is the row with the same primary key,
     * the new object will not be inserted
     *
     * @param obj the object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertIgnore(obj: T): Single<Long>

    /**
     * Insert an object in the database. Ignores if there is the row with the same primary key,
     * the new object will not be inserted
     *
     * @param obj the object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertIgnoreSync(obj: T): Long

    /**
     * Insert an array of objects in the database. If there is the row with the same primary key,
     * this object will be updated
     *
     * @param list the objects to be inserted
     */
    fun insertOrUpdate(list: List<T>): Completable {
        return Completable.create { subscriber ->
            list.forEach { insertOrUpdateSync(it) }
            subscriber.onComplete()
        }
    }

    /**
     * Insert an object in the database.  If there is the row with the same primary key,
     * this object will be updated
     *
     * @param obj the object to be inserted
     */
    fun insertOrUpdate(obj: T): Completable {
        return Completable.create { subscriber ->
            insertOrUpdateSync(obj)
            subscriber.onComplete()
        }
    }

    private fun insertOrUpdateSync(obj: T) {
        if (insertIgnoreSync(obj) == -1L)
            updateSync(obj)
    }
}