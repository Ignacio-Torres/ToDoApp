package cl.ciisa.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import cl.ciisa.todoapp.controllers.AuthController
import cl.ciisa.todoapp.models.User
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

        //boton para registrarse
        btnRegister.setOnClickListener {
            val email = tilEmail.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()
            //valido el email, para que tenga el formato correcto y no venga vacio
            val emailValid = TextInputLayoutValidator(tilEmail)
                .required()
                .email()
                .isValid()
            //valido la contraseña para que tenga entre 8 y 15 caracteres, contenga 1 mayuscula y 1 minuscula, que contenga un numero y tenga tambien un caracter especial
            val passwordValid = TextInputLayoutValidator(tilPassword)
                .required()
                .password()
                .haveLowercase()
                .haveUppercase()
                .haveNumber()
                .haveSpecialChar()
                .isValid()
            //reviso si el email y la contraseña  son validos
            if (emailValid && passwordValid) {
                val user = User(id = null,
                    email = email,
                    password = password,
                    active=true)
                //si la contraseña y el email son validos, lo envio al login
                AuthController(this).register(user)
            } else {
                Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show()
            }
        }
        //boton cancelar
        btnCancel.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}