package cg.vsu.survey.network.question

import cg.vsu.survey.utils.DEFAULT_JWT
import cg.vsu.survey.app.RetrofitInstance
import cg.vsu.survey.model.Question

object QuestionRestRepository {
    private val api: QuestionAPIService

    init {
        val retrofit = RetrofitInstance.instance
        api = retrofit.create(QuestionAPIService::class.java)
    }

    // Создание нового вопроса
    suspend fun createQuestion(
        question: Question,
        token: String
    ): Question? {
        val response = api.createQuestion("Bearer $token", question)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Получение вопроса по ID
    suspend fun getQuestion(
        questionId: Int,
        token: String
    ): Question? {
        val response = api.getQuestion("Bearer $token", questionId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Обновление вопроса по ID
    suspend fun updateQuestion(
        questionId: Int,
        question: Question,
        token: String
    ): Question? {
        val response = api.updateQuestion("Bearer $token", questionId, question)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Удаление вопроса по ID
    suspend fun deleteQuestion(
        questionId: Int,
        token: String
    ): String? {
        val response = api.deleteQuestion("Bearer $token", questionId)
        return if (response.isSuccessful) {
            response.body()?.get("message")
        } else {
            // Обработка ошибок
            null
        }
    }

    // Получение всех вопросов для конкретного опроса
    suspend fun getQuestionsBySurvey(
        surveyId: Int,
        token: String
    ): List<Question>? {
        val response = api.getQuestionsBySurvey("Bearer $token", surveyId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }
}
