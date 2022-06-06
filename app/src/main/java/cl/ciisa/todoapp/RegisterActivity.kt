package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import cl.ciisa.todoapp.controllers.AuthController
import cl.ciisa.todoapp.utils.TextInputLayoutValidator
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.activity_register_btn_register)
        val btnCancel = findViewById<Button>(R.id.activity_register_btn_cancel)
        val tilEmail = findViewById<TextInputLayout>(R.id.activity_register_til_email)
        val tilPassword = findViewById<TextInputLayout>(R.id.activity_register_til_password)

        btnRegister.setOnClickListener {
            val email = tilEmail.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()

            val emailValid = TextInputLayoutValidator(tilEmail)
                .required()
                .email()
                .isValid()
            val passwordValid = TextInputLayoutValidator(tilPassword)
                .required()
                .password()
                .haveLowercase()
                .haveUppercase()
                .haveNumber()
                .haveSpecialChar()
                .isValid()
            if (emailValid && passwordValid) {
                AuthController(this).register(email, password)
            } else {
                Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancel.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}