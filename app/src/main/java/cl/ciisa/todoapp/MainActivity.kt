package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fabAdd = findViewById<Button>(R.id.activity_main_btn_add)
        val fabToDelete = findViewById<Button>(R.id.activity_main_btn_delete)
        val tvToDoTask = findViewById<TextView>(R.id.activity_main_tv_toDoTask_0)
        val tvCloseSession = findViewById<TextView>(R.id.activity_main_tv_close_session)
        tvToDoTask.setOnClickListener {
            val intent = Intent(this, EditTaskActivity::class.java)
            startActivity(intent)
        }
        tvCloseSession.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        fabAdd.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
        fabToDelete.setOnClickListener{
            Toast.makeText(this,"TAREA BORRADA",Toast.LENGTH_SHORT)
        }
    }
}