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
        //boton login
        btnLogin.setOnClickListener {
            val email = tilEmail.editText?.text.toString().trim()
            val password = tilPassword.editText?.text.toString().trim()
            //valido el texto del campo email, para saber si es un email valido, y no esta vacio
            val emailValid = TextInputLayoutValidator(tilEmail)
                .required()
                .email()
                .isValid()
            //valido la contraseña, para que no venga vacia
            val passwordValid = TextInputLayoutValidator(tilPassword)
                .required()
                .isValid()
            //valido si la contraseña y el email son validos, sino se muestra el mensaje de campos invalidos
            if (emailValid && passwordValid) {
                //valido que el email y la contraseña sean correctos para hacer el login
                AuthController(this).login(email, password)
            } else {
                Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show()
            }
        }
        //boton para ir al registro
        btnGoToRegister.setOnClickListener {

            val intent = Intent(this,RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}