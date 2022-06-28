package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import cl.ciisa.todoapp.controllers.TaskController
import cl.ciisa.todoapp.models.Task
import java.text.SimpleDateFormat

class TaskItemActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_item)

        val task = intent.getSerializableExtra("task") as Task
        val tvTitle = findViewById<TextView>(R.id.activity_task_item_tv_title)
        val btnDelete = findViewById<Button>(R.id.activity_task_item_btn_delete)
        val btnEdit = findViewById<Button>(R.id.activity_task_item_btn_edit)
        val btnBack = findViewById<Button>(R.id.activity_task_item_btn_back)
        val tvDescription = findViewById<TextView>(R.id.activity_task_item_tv_description)
        val tvPriority = findViewById<TextView>(R.id.activity_task_item_tv_priority)
        val tvLimitDate = findViewById<TextView>(R.id.activity_task_item_tv_limit_date)
        //Tareas
        val bundle = Bundle()
        tvTitle.text = task.title
        tvDescription.text = task.description
        tvPriority.text = task.priority
        tvLimitDate.text = task.finishDate.toString()
        val taskId = task.id!!.toLong()
        bundle.putString("title",task.title)
        bundle.putString("description",task.description)
        bundle.putString("priority",task.priority)
        bundle.putString("finishDate",task.finishDate.toString())
        bundle.putString("taskId",taskId.toString())
        //boton editar tarea
        btnEdit.setOnClickListener{
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        //boton volver
        btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        //boton borrar tarea
        btnDelete.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Si") { dialog, id ->
                    TaskController(this).delete(taskId)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

    }
}