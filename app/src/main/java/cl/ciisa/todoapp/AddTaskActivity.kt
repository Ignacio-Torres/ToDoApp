package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import cl.ciisa.todoapp.controllers.AuthController
import cl.ciisa.todoapp.utils.TextInputLayoutValidator
import cl.ciisa.todoapp.utils.showDatePickerDialog
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val btnCreate = findViewById<Button>(R.id.activity_add_task_btn_add)
        val btnCancel = findViewById<Button>(R.id.activity_add_task_btn_cancel)
        val tilTaskTitle = findViewById<TextInputLayout>(R.id.activity_add_task_til_task_title)
        val tilTaskDescription = findViewById<TextInputLayout>(R.id.activity_add_task_til_task_description)
        val spnPriority = findViewById<Spinner>(R.id.activity_add_task_spn_priority)
        val tilDateFinishTask = findViewById<TextInputLayout>(R.id.activity_edit_task_til_date_finish)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.prioritys_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnPriority.adapter = adapter

        tilDateFinishTask.editText?.setOnClickListener { _ ->
            showDatePickerDialog(this, tilDateFinishTask, Date())
        }
        //boton crear tarea
        btnCreate.setOnClickListener{
            val textInputLayoutTaskTitle = tilTaskTitle.editText?.text.toString()
            //valido la tarea por agregar, para que sea requerida y no venga vacia
            val textInputLayoutTaskTitleValid = TextInputLayoutValidator(tilTaskTitle)
                .required()
                .isValid()
            val textInputLayoutDateFinishTaskValid = TextInputLayoutValidator(tilDateFinishTask)
                .required()
                .dateAfter(Date())
                .isValid()
            //si el texto es valido, se agrega, sino muestra el error de campo invalido
            if (textInputLayoutTaskTitleValid && textInputLayoutDateFinishTaskValid) {
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