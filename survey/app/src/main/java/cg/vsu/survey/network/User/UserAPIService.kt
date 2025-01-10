package cg.vsu.survey.network.User

import cg.vsu.survey.model.LoginResponse
import cg.vsu.survey.model.User
import cg.vsu.survey.model.UserRegistration
import retrofit2.Response
import retrofit2.http.*
interface UserAPIService {

    // Вход пользователя
    @POST("/surveys/api/login/")
    suspend fun loginUser(
        @Body credentials: UserRegistration
    ): Response<LoginResponse>

    // Создание пользователя
    @POST("/surveys/api/users/")
    suspend fun createUser(
        @Body user: UserRegistration
    ): Response<LoginResponse>

    // Получение всех пользователей
    @GET("/surveys/api/users/all/")
    suspend fun getAllUsers(
        @Header("Authorization") token: String
    ): Response<List<User>>

    // Получение пользователя по ID
    @GET("/surveys/api/users/{id}/")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<User>

    // Обновление пользователя
    @PUT("/surveys/api/users/{id}/")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body user: User
    ): Response<User>

    // Удаление пользователя
    @DELETE("/surveys/api/users/{id}/")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Void>

    // Получение администраторов опроса по ID
    @GET("/surveys/api/users/survey/{id}/")
    suspend fun getSurveyAdmins(
        @Header("Authorization") token: String,
        @Path("id") surveyId: Int
    ): Response<List<User>>
    }


