package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import cl.ciisa.todoapp.controllers.AuthController
import cl.ciisa.todoapp.utils.TextInputLayoutValidator
import com.google.android.material.textfield.TextInputLayout

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val btnCreate = findViewById<Button>(R.id.activity_add_task_btn_add)
        val btnCancel = findViewById<Button>(R.id.activity_add_task_btn_cancel)
        val tilTaskAdd = findViewById<TextInputLayout>(R.id.activity_add_task_til_task_add)

        //boton crear tarea
        btnCreate.setOnClickListener{
            val textInputLayoutTaskAdd = tilTaskAdd.editText?.text.toString()
            val textInputLayoutTaskAddValid = TextInputLayoutValidator(tilTaskAdd).required().isValid()
            if (textInputLayoutTaskAddValid) {
                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                Toast.makeText(this, "Campo inválido", Toast.LENGTH_SHORT).show()
            }
        }
        //boton cancelar
        btnCancel.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}