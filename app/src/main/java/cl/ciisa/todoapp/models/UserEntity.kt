package cl.ciisa.todoapp.models


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["email"],unique = true)])
data class UserEntity (
    @PrimaryKey(autoGenerate = true) val id :Long?,
    val email: String,
    val password: String,
    val active: Boolean
    )