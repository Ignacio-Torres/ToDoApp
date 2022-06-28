package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import cl.ciisa.todoapp.controllers.AuthController
import cl.ciisa.todoapp.controllers.TaskController
import cl.ciisa.todoapp.ui.TaskAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allTasks = TaskController(this).getAll()
        val lvTasks = findViewById<ListView>(R.id.activity_main_lv_tasks)
        val adapter = TaskAdapter(this,allTasks)
        lvTasks.adapter = adapter

        val fabAdd = findViewById<Button>(R.id.activity_main_btn_add)
        val tvCloseSession = findViewById<TextView>(R.id.activity_main_tv_close_session)

        lvTasks.setOnItemClickListener { adapterView, view, i, l ->
            run {
                val task = allTasks[i]
                val intent = Intent(view.context, TaskItemActivity::class.java)
                intent.putExtra("task", task)
                view.context.startActivity(intent)
            }
        }
        //cerrar sesion
        tvCloseSession.setOnClickListener{
            val controller = AuthController(this)
            controller.clearSession()
        }
        //agregar tarea
        fabAdd.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}