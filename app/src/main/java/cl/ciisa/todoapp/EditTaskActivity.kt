package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import cl.ciisa.todoapp.controllers.TaskController
import cl.ciisa.todoapp.models.Task
import cl.ciisa.todoapp.utils.TextInputLayoutValidator
import cl.ciisa.todoapp.utils.showDatePickerDialog
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
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
        //Tareas anteriores
        val bundle:Bundle?=intent.extras
        val title = bundle!!.getString("title").toString()
        val description = bundle!!.getString("description").toString()
        val taskId = bundle!!.getString("taskId").toString()
        val priority = bundle!!.getString("priority").toString()
        val re = Regex("\\D")
        val priorityNumber = re.replace(priority, "").toInt()
        val finishDate = bundle!!.getString("finishDate").toString()
        tilEditTaskTitle.editText?.setText(title)
        tilEditTaskDescription.editText?.setText(description)
        spnPriority.setSelection(priorityNumber-1)
        tilDateFinishTask.editText?.setText(finishDate)

        btnEdit.setOnClickListener{

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
                val id = taskId.toLong()
                val newPriority = spnPriority.selectedItem.toString()
                val newDateFinishTask = tilDateFinishTask.editText?.text.toString()
                val newTitle = tilEditTaskTitle.editText?.text.toString()
                val newDescription = tilEditTaskDescription.editText?.text.toString()
                val task = Task(
                    id = id,
                    title = newTitle,
                    description = newDescription,
                    priority = newPriority,
                    finishDate = SimpleDateFormat("yyyy-MM-dd").parse(newDateFinishTask),
                    done = false
                )
                TaskController(this).update(task)
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