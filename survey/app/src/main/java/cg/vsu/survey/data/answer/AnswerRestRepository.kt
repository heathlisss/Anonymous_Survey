package cg.vsu.survey.data.answer

import cg.vsu.survey.app.DEFAULT_JWT
import cg.vsu.survey.app.RetrofitInstance
import cg.vsu.survey.model.Answer

object AnswerRestRepository {
    private val api: AnswerAPIService

    init {
        val retrofit = RetrofitInstance.instance
        api = retrofit.create(AnswerAPIService::class.java)
    }

    // Сохранение всех ответов пользователя на опрос
    suspend fun submitAnswers(
        userId: Int,
        surveyId: Int,
        answers: List<Answer>,
        token: String = DEFAULT_JWT
    ): String? {
        val response = api.submitAnswers("Bearer $token", userId, surveyId, answers)
        return if (response.isSuccessful) {
            response.body()?.get("message")
        } else {
            // Обработка ошибок
            null
        }
    }

    // Удаление всех ответов пользователя на опрос
    suspend fun deleteAnswers(
        userId: Int,
        surveyId: Int,
        token: String = DEFAULT_JWT
    ): String? {
        val response = api.deleteAnswers("Bearer $token", userId, surveyId)
        return if (response.isSuccessful) {
            response.body()?.get("message")
        } else {
            // Обработка ошибок
            null
        }
    }
}

