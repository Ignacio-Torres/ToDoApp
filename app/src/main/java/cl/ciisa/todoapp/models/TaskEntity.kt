package cl.ciisa.todoapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val title: String,
    val priority: String,
    val description: String?,
    @ColumnInfo(name = "finish_date") val finishDate: Date,
    val done: Boolean = false,
    @ColumnInfo(name = "user_id") val userId: Long
)