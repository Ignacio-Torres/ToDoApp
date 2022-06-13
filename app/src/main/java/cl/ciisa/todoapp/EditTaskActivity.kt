package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import cl.ciisa.todoapp.models.Task
import cl.ciisa.todoapp.utils.TextInputLayoutValidator
import cl.ciisa.todoapp.utils.showDatePickerDialog
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class EditTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val btnEdit = findViewById<Button>(R.id.activity_edit_task_btn_edit)
        val btnCancel = findViewById<Button>(R.id.activity_edit_task_btn_cancel)
        val tilEditTaskTitle = findViewById<TextInputLayout>(R.id.activity_edit_task_til_task_title)
        val tilEditTaskDescription = findViewById<TextInputLayout>(R.id.activity_edit_task_til_task_description)
        val spnPriority = findViewById<Spinner>(R.id.activity_add_task_spn_priority)
        val tilDateFinishTask = findViewById<TextInputLayout>(R.id.activity_add_task_til_date_finish)

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

        //boton editar tarea
        btnEdit.setOnClickListener{
            val priority = spnPriority.selectedItem
            val dateFinishTask = tilDateFinishTask.editText?.text.toString()
            val textInputLayoutEditTaskText = tilEditTaskTitle.editText?.text.toString()
            //valido la tarea para que no este vacia y sea requerida
            val textInputLayoutDateFinishTaskValid = TextInputLayoutValidator(tilDateFinishTask)
                .required()
                .dateAfter(Date())
                .isValid()
            val textInputLayoutEditTaskTextValid = TextInputLayoutValidator(tilEditTaskTitle)
                .required()
                .isValid()
            //valido que el texto a editar sea valido, y lo guarda, sino muestra el error de campo invalido
            if (textInputLayoutEditTaskTextValid && textInputLayoutDateFinishTaskValid) {
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