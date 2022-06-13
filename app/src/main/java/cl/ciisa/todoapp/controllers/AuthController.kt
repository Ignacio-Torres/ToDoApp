package cl.ciisa.todoapp.controllers

import android.content.Context
import android.content.Intent
import android.widget.Toast
import cl.ciisa.todoapp.LoginActivity
import cl.ciisa.todoapp.MainActivity
import cl.ciisa.todoapp.RegisterActivity

class AuthController constructor(context: Context) {
    private val context = context

    fun login(email: String, password: String){
        if(email=="asd@gmail.com" && password=="12345"){
            Toast.makeText(this.context, "Bienvenido ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this.context, MainActivity::class.java)
            this.context.startActivity(intent)
        }else{
            Toast.makeText(this.context, "Email y/o contraseña incorrecta ", Toast.LENGTH_SHORT).show()
        }

    }
    fun register(email: String, password: String){
        if(email!="asd@gmail.com"){
            Toast.makeText(this.context, "Registrado correctamente ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this.context, LoginActivity::class.java)
            this.context.startActivity(intent)
        }else{
            Toast.makeText(this.context, "Email registrado anteriormente ", Toast.LENGTH_SHORT).show()
        }
    }
}