package cl.ciisa.todoapp.controllers

import android.content.Context
import cl.ciisa.todoapp.models.Task
import com.google.type.DateTime
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class TaskController constructor(context: Context) {
    private val context = context

    fun getAll(): List<Task>{
        val tasks = ArrayList<Task>()
        val date = Date()
        val calendar = Calendar.getInstance()
        for(i in 1..10){
            calendar.time = date
            calendar.add(Calendar.DATE,(0..10).random())
            val randomDate = calendar.time
            tasks.add(Task(
                id = i.toLong(),
                title = "Tarea $i",
                description = "Descripcion $i",
                done = false,
                finishDate = randomDate,
                priority = (0..5).random().toShort()
            ))
        }
        return tasks
    }

}