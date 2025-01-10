package cg.vsu.survey.network.survey

import cg.vsu.survey.utils.DEFAULT_JWT
import cg.vsu.survey.app.RetrofitInstance
import cg.vsu.survey.model.Survey

object SurveyRestRepository {
    private val api: SurveyAPIService

    init {
        val retrofit = RetrofitInstance.instance
        api = retrofit.create(SurveyAPIService::class.java)
    }

    // Поиск опросов
    suspend fun searchSurveys(
        query: String,
        token: String
    ): List<Survey>? {
        val response = api.searchSurveys("Bearer $token", query)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Получение всех опросов
    suspend fun getAllSurveys(
        token: String
    ): List<Survey>? {
        val response = api.getAllSurveys("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Создание нового опроса
    suspend fun createSurvey(
        survey: Survey,
        token: String
    ): Survey? {
        val response = api.createSurvey("Bearer $token", survey)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Получение опроса по ID
    suspend fun getSurveyById(
        id: Int,
        token: String
    ): Survey? {
        val response = api.getSurveyById("Bearer $token", id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Обновление опроса
    suspend fun updateSurvey(
        id: Int,
        survey: Survey,
        token: String
    ): Survey? {
        val response = api.updateSurvey("Bearer $token", id, survey)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Удаление опроса
    suspend fun deleteSurvey(
        id: Int,
        token: String
    ): Boolean {
        val response = api.deleteSurvey("Bearer $token", id)
        return response.isSuccessful
    }

    // Получение опросов пользователя
    suspend fun getUserSurveys(
        userId: Int,
        token: String
    ): List<Survey>? {
        val response = api.getUserSurveys("Bearer $token", userId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }
}