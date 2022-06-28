package cl.ciisa.todoapp.models

import java.util.*

data class User(
    val id :Long?,
    val email: String,
    val password: String,
    val active: Boolean
)
