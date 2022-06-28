package cl.ciisa.todoapp.models

import java.io.Serializable
import java.util.*

data class Task(
    val id :Long?,
    val title: String,
    val description: String?,
    val priority: String,
    val finishDate: Date,
    val done: Boolean = false) : Serializable{
}