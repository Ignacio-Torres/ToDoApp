package cl.ciisa.todoapp.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import cl.ciisa.todoapp.LoginActivity
import cl.ciisa.todoapp.MainActivity
import cl.ciisa.todoapp.lib.AppDatabase
import cl.ciisa.todoapp.lib.BCrypt
import cl.ciisa.todoapp.models.User
import cl.ciisa.todoapp.models.UserEntity

const val USER_ID = "user_id"
const val INCORRECT_CREDENTIALS = "Email y/o contraseña incorrecta"
class AuthController constructor(context: Context) {
    private val sharedPref = context.getSharedPreferences("TODO_APP",Context.MODE_PRIVATE)
    private val context = context
    private val dao = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "database-name"
    ).allowMainThreadQueries().fallbackToDestructiveMigration()
        .build()
        .userDao()
    fun login(email: String, password: String){
        val user = dao.findByEmail(email)
        if(user==null){
            Toast.makeText(this.context, INCORRECT_CREDENTIALS, Toast.LENGTH_SHORT).show()
            return
        }
        if(BCrypt.checkpw(password, user.password)){
            Toast.makeText(this.context, "Bienvenido ", Toast.LENGTH_SHORT).show()
            val sharedEdit = sharedPref.edit()
            sharedEdit.putLong("user_id",user.id!!)
            sharedEdit.commit()
            val intent = Intent(this.context, MainActivity::class.java)
            this.context.startActivity(intent)
            (this.context as Activity).finish()
        }else{
            Toast.makeText(this.context, INCORRECT_CREDENTIALS, Toast.LENGTH_SHORT).show()
        }
    }
    fun register(user: User){
        val hashedPassword = BCrypt.hashpw(user.password,BCrypt.gensalt())
        val userEntity = UserEntity(
            id = null,
            email = user.email,
            password = hashedPassword,
            active = true
        )
        try{
            dao.insert(userEntity)
            Toast.makeText(this.context, "Registrado correctamente ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this.context, LoginActivity::class.java)
            this.context.startActivity(intent)
        }catch(e: Exception){
            Toast.makeText(this.context, "Cuenta existente", Toast.LENGTH_SHORT).show()
        }
    }
    fun checkUserSession() {
        val id = sharedPref.getLong("user_id", -1)
        Handler().postDelayed({
            if (id == (-1).toLong()) {
                val intent = Intent(this.context, LoginActivity::class.java)
                this.context.startActivity(intent)
            } else {
                val intent = Intent(this.context, MainActivity::class.java)
                this.context.startActivity(intent)
            }
            (this.context as Activity).finish()
        }, 2000)
    }
    fun clearSession(){
        val editor = sharedPref.edit()
        editor.remove("user_id")
        editor.commit()
        val intent = Intent(this.context, LoginActivity::class.java)
        this.context.startActivity(intent)
        (this.context as Activity).finish()
    }
}