package cg.vsu.survey.network.User

import android.util.Log
import cg.vsu.survey.app.RetrofitInstance
import cg.vsu.survey.model.User
import cg.vsu.survey.model.UserAuth


object UserRestRepository {
    private val api: UserAPIService

    init {
        val retrofit = RetrofitInstance.instance
        api = retrofit.create(UserAPIService::class.java)
    }

    // Вход пользователя
    suspend fun loginUser(credentials: UserAuth): Pair<User, String>? {
        Log.d("начинаем сохранять", ": $credentials")
        val response = api.loginUser(credentials)
        return if (response.isSuccessful) {
            val loginResponse = response.body()
            loginResponse?.let {
                val user = User(
                    id = it.id,
                    username = it.username,
                    email = it.email ?: "",
                    admin = it.admin,
                    answered_surveys = it.answered_surveys,
                    created_surveys = it.created_surveys
                )
                val token = it.token
                Pair(user, token ?: "")
            }
        } else {
            null
        }
    }

    // Создание пользователя
    suspend fun createUser(user: UserAuth): Pair<User, String>? {
        Log.d("начинаем сохранять", ": $user")
        val response = api.createUser(user)
        return if (response.isSuccessful) {
            val createUser = response.body()
            createUser?.let {
                val user = User(
                    id = it.id,
                    username = it.username,
                    email = it.email ?: "",
                    admin = it.admin,
                    answered_surveys = it.answered_surveys,
                    created_surveys = it.created_surveys
                )
                val token = it.token
                Pair(user, token ?: "")
            }
        } else {
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
    suspend fun getUserById(
        id: Int,
        token: String
    ): User? {
        val response = api.getUserById("Bearer $token", id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Обновление пользователя
    suspend fun updateUser(
        id: Int,
        user: User,
        token: String
    ): User? {
        val response = api.updateUser("Bearer $token", id, user)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Удаление пользователя
    suspend fun deleteUser(
        id: Int,
        token: String
    ): Boolean {
        val response = api.deleteUser("Bearer $token", id)
        return response.isSuccessful
    }

    // Получение администраторов опроса по ID
    suspend fun getSurveyAdmins(
        surveyId: Int,
        token: String
    ): List<User>? {
        val response = api.getSurveyAdmins("Bearer $token", surveyId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }
}