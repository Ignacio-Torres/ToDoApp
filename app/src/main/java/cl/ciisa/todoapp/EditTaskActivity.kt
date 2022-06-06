package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import cl.ciisa.todoapp.utils.TextInputLayoutValidator
import com.google.android.material.textfield.TextInputLayout

class EditTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val btnEdit = findViewById<Button>(R.id.activity_edit_task_btn_edit)
        val btnCancel = findViewById<Button>(R.id.activity_edit_task_btn_cancel)
        val tilEditTaskText = findViewById<TextInputLayout>(R.id.activity_edit_task_til_task_edit)
        btnEdit.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)

            val textInputLayoutEditTaskText = tilEditTaskText.editText?.text.toString()
            val textInputLayoutEditTaskTextValid = TextInputLayoutValidator(tilEditTaskText).required().isValid()
            if (textInputLayoutEditTaskTextValid) {
                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                Toast.makeText(this, "Campo inválido", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancel.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}