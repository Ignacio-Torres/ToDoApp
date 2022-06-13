package cl.ciisa.todoapp.utils

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import cl.ciisa.todoapp.ui.DatePickerFragment
import com.google.android.material.textfield.TextInputLayout
import java.util.*

fun showDatePickerDialog(activity: AppCompatActivity, textInputLayout: TextInputLayout, initialDate: Date) {
    val listener = DatePickerDialog.OnDateSetListener { activity, year, month, day ->
        textInputLayout.editText?.setText(String.format("%d-%02d-%02d", year, (month + 1), day))
    }
    val fragment = DatePickerFragment(listener, initialDate)
    fragment.show(activity.supportFragmentManager, "datePicker")
}