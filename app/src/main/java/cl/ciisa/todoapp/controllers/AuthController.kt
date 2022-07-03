package cl.ciisa.todoapp.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import androidx.room.Room
import retrofit2.Callback;
import cl.ciisa.todoapp.LoginActivity
import cl.ciisa.todoapp.MainActivity
import cl.ciisa.todoapp.lib.AppDatabase
import cl.ciisa.todoapp.lib.BCrypt
import cl.ciisa.todoapp.lib.RetrofitClient
import cl.ciisa.todoapp.models.*
import cl.ciisa.todoapp.services.AuthService
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

const val USER_ID = "user_id"
const val INCORRECT_CREDENTIALS = "Email y/o contraseña incorrecta"


class AuthController constructor(context: Context) {
    private val sharedPref = context.getSharedPreferences("TODO_APP",Context.MODE_PRIVATE)
    private val context = context
    private val retrofit = RetrofitClient.getRetrofitInstance()
    private val authService = retrofit.create(AuthService::class.java)

    private val dao = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "database-name"
    ).allowMainThreadQueries().fallbackToDestructiveMigration()
        .build()
        .userDao()

    fun login(email: String, password: String){
        val loginPayload = LoginPayloadDTO(email, password)
        val call = authService.login(loginPayload)

        call.enqueue(object : Callback<LoginResponseDTO> {
            override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<LoginResponseDTO>,
                response: Response<LoginResponseDTO>
            ) {
                if (response.code() != 200) {
                    Toast.makeText(context, INCORRECT_CREDENTIALS, Toast.LENGTH_SHORT).show()
                } else {
                    val bodyResponse = response.body()
                    Toast.makeText(context, "Bienvenido ${bodyResponse?.user?.id}", Toast.LENGTH_SHORT).show()
                    val sharedEdit = sharedPref.edit()
                    sharedEdit.putLong("user_id", bodyResponse?.user?.id!!)
                    sharedEdit.putString("user_jwt", bodyResponse?.jwt!!)
                    sharedEdit.commit()

                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }
        })
    }
    fun register(user: User){
        val registerPayload = RegisterPayloadDTO(
            user.email,
            user.email,
            user.password
        )

        val call = authService.register(registerPayload)

        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.code() != 200) {
                    Toast.makeText(context, "Cuenta existente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Cuenta registrada", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    context.startActivity(intent)
                }
            }
        })
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
        editor.remove("user_jwt")
        editor.commit()
        val intent = Intent(this.context, LoginActivity::class.java)
        this.context.startActivity(intent)
        (this.context as Activity).finish()
    }
}