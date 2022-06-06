package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cl.ciisa.todoapp.controllers.AuthController
import cl.ciisa.todoapp.utils.TextInputLayoutValidator
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        val btnLogin = findViewById<Button>(R.id.activity_login_btn_login)
        val btnGoToRegister = findViewById<Button>(R.id.activity_login_btn_to_register)
        val tilEmail = findViewById<TextInputLayout>(R.id.activity_login_til_email)
        val tilPassword = findViewById<TextInputLayout>(R.id.activity_login_til_password)
        btnLogin.setOnClickListener {
            val email = tilEmail.editText?.text.toString().trim()
            val password = tilPassword.editText?.text.toString().trim()

            val emailValid = TextInputLayoutValidator(tilEmail)
                .required()
                .email()
                .isValid()
            val passwordValid = TextInputLayoutValidator(tilPassword)
                .required()
                .isValid()
            if (emailValid && passwordValid) {
                AuthController(this).login(email, password)
            } else {
                Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show()
            }
        }
        btnGoToRegister.setOnClickListener {

            val intent = Intent(this,RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}