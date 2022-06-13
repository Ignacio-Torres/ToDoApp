package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import cl.ciisa.todoapp.models.Task

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

        tvTitle.text = task.title
        tvDescription.text = task.description
        tvPriority.text = task.priority.toString()
        tvLimitDate.text = task.finishDate.toString()

        btnEdit.setOnClickListener{
            val intent = Intent(this, EditTaskActivity::class.java)
            startActivity(intent)
        }
        btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        btnDelete.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            Toast.makeText(this,"TAREA BORRADA", Toast.LENGTH_SHORT).show()
        }

    }
}