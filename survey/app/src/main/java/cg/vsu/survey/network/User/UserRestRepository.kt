package cg.vsu.survey.network.User

import cg.vsu.survey.app.RetrofitInstance
import cg.vsu.survey.model.LoginCredentials
import cg.vsu.survey.model.User
import cg.vsu.survey.model.UserRegistration


object UserRestRepository {
    private val api: UserAPIService

    init {
        val retrofit = RetrofitInstance.instance
        api = retrofit.create(UserAPIService::class.java)
    }

    // Вход пользователя
    suspend fun loginUser(credentials: LoginCredentials): User? {
        val response = api.loginUser(credentials)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок, можно вернуть null или другую структуру для ошибок
            null
        }
    }

    // Создание пользователя
    suspend fun createUser(user: UserRegistration): User? {
        val response = api.createUser(user)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Получение всех пользователей
    suspend fun getAllUsers(token: String): List<User>? {
        val response = api.getAllUsers("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Получение пользователя по ID
    suspend fun getUserById(token: String, id: Int): User? {
        val response = api.getUserById("Bearer $token", id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Обновление пользователя
    suspend fun updateUser(token: String, id: Int, user: User): User? {
        val response = api.updateUser("Bearer $token", id, user)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Удаление пользователя
    suspend fun deleteUser(token: String, id: Int): Boolean {
        val response = api.deleteUser("Bearer $token", id)
        return response.isSuccessful
    }

    // Получение администраторов опроса по ID
    suspend fun getSurveyAdmins(token: String, surveyId: Int): List<User>? {
        val response = api.getSurveyAdmins("Bearer $token", surveyId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }
}