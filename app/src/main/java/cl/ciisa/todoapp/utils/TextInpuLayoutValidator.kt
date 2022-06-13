package cl.ciisa.todoapp.utils

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import java.util.regex.Pattern
import java.text.SimpleDateFormat
import java.lang.Exception

class TextInputLayoutValidator constructor(textInputLayout: TextInputLayout) {
    private val textInputLayout: TextInputLayout = textInputLayout
    private val value: String = textInputLayout.editText?.text.toString()
    private var required: Boolean = false
    private var invalid: Boolean = false
    private val patternPasswordLength = ".{8,15}".toRegex()
    private val patternHaveSpecialChar = ".*[!@#$%^&*+=?-].*".toRegex()
    private val patternHaveNumber = ".*\\d.*".toRegex()
    private val patternHaveUppercase = ".*[A-Z].*".toRegex()
    private val patternHaveLowercase = ".*[a-z].*".toRegex()
    private val formatter = SimpleDateFormat("yyyy-MM-dd")

    private fun setError(invalid: Boolean, message:String){
        if(invalid){
            this.invalid = true
            textInputLayout.error = message
        }else{
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        }
    }
    private fun mustValidate(): Boolean {
        return (!this.required && this.value.isNotEmpty() || !invalid)
    }
    fun required(): TextInputLayoutValidator {
        this.required = true
        val invalidField = this.value.isEmpty()
        this.setError(invalidField, "Campo requerido")
        return this
    }
    fun email(): TextInputLayoutValidator {
        if (mustValidate()) {
            val invalidField = !Patterns.EMAIL_ADDRESS.matcher(this.value).matches()
            this.setError(invalidField, "El valor debe ser un email válido")
        }
        return this
    }
    fun password(): TextInputLayoutValidator{
        if(mustValidate()){
            val invalidLengthField = !this.value.matches(patternPasswordLength)
            this.setError(invalidLengthField,"La contraseña debe tener entre 8 y 15 caracteres")
        }
        return this
    }
    fun haveNumber():TextInputLayoutValidator{
        if(mustValidate()){
            val invalidFieldNumber = !this.value.matches(patternHaveNumber)
            this.setError(invalidFieldNumber,"La contraseña debe llevar al menos un numero")
        }
        return this
    }
    fun haveSpecialChar():TextInputLayoutValidator{
        if(mustValidate()){
            val invalidFieldSpecialChar = !this.value.matches(patternHaveSpecialChar)
            this.setError(invalidFieldSpecialChar,"La contraseña debe llevar al menos un caracter especial")
        }
        return this
    }
    fun haveUppercase():TextInputLayoutValidator{
        if(mustValidate()){
            val invalidField = !this.value.matches(patternHaveUppercase)
            this.setError(invalidField, "La contraseña debe llevar al menos una mayuscula")
        }
        return this
    }
    fun haveLowercase():TextInputLayoutValidator{
        if(mustValidate()){
            val invalidField = !this.value.matches(patternHaveLowercase)
            this.setError(invalidField,"La contraseña debe llevar al menos una minuscula")
        }
        return this
    }
    fun dateAfter(date: Date): TextInputLayoutValidator {
        if (mustValidate()) {
            var invalidField = false
            try {
                val dateValue = formatter.parse(this.value)
                invalidField = !dateValue.after(date)
            } catch (e: Exception) {
                invalidField = true
            }
            this.setError(invalidField, "La fecha no puede ser anterior a ${formatter.format(date)}")
        }
        return this
    }

    fun isValid(): Boolean {
        return !this.invalid
    }
}