package cl.ciisa.todoapp.services

import cl.ciisa.todoapp.models.LoginPayloadDTO
import cl.ciisa.todoapp.models.LoginResponseDTO
import cl.ciisa.todoapp.models.RegisterPayloadDTO
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call


interface AuthService {
    @POST("/api/auth/local")
    fun login(@Body payload: LoginPayloadDTO): Call<LoginResponseDTO>

    @POST("/api/auth/local/register")
    fun register(@Body payload: RegisterPayloadDTO): Call<Void>
}