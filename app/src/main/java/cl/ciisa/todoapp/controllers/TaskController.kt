package cl.ciisa.todoapp.controllers

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.room.Room
import cl.ciisa.todoapp.MainActivity
import cl.ciisa.todoapp.R
import cl.ciisa.todoapp.lib.AppDatabase
import cl.ciisa.todoapp.models.Task
import cl.ciisa.todoapp.models.TaskEntity
import java.util.*


class TaskController constructor(context: Context, userId: Long = 0) {
    private val context = context
    private val sharedPref = context.getSharedPreferences("TODO_APP", Context.MODE_PRIVATE)
    private val userId = sharedPref.getLong("user_id", 0)
    private val dao = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    )
        .allowMainThreadQueries().fallbackToDestructiveMigration()
        .build()
        .taskDao()
    fun getAll(): List<Task>{
        val taskEntities = dao.findAll(userId)
        val tasks = ArrayList<Task>()

        taskEntities.forEach { task -> tasks.add(Task(
            id = task.id,
            title = task.title,
            description = task.description,
            priority = task.priority,
            finishDate = task.finishDate,
            done = task.done
        )) }
        return tasks
    }
    fun getById(id: Long): Task? {
        val entity = dao.findById(id) ?: return null

        return Task(
            id = entity?.id,
            title = entity.title,
            description = entity.description,
            done = entity.done,
            priority = entity.priority,
            finishDate = entity.finishDate
        )
    }
    fun delete(id: Long) {
        dao.delete(id)
    }
    fun update(task: Task) {
        val entity = TaskEntity(
            id = task.id,
            title = task.title,
            description = task.description,
            done = task.done,
            priority = task.priority,
            finishDate = task.finishDate,
            userId = sharedPref.getLong("user_id", -1)
        )
        dao.update(entity)
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        (this.context as Activity).finish()
    }

    fun create (task: Task) {
        val entity = TaskEntity(
            id = null,
            title = task.title,
            description = task.description,
            done = task.done,
            priority = task.priority,
            finishDate = task.finishDate,
            userId = sharedPref.getLong("user_id", -1)
        )
        dao.insert(entity)
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        (this.context as Activity).finish()
    }
}