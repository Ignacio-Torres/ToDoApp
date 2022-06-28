package cl.ciisa.todoapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cl.ciisa.todoapp.models.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM users WHERE email like :email LIMIT 1")
    fun findByEmail(email:String): UserEntity?

    @Insert
    fun insert(user: UserEntity)
}