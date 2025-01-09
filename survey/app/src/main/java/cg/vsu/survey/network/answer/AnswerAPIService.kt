package cg.vsu.survey.network.answer

import cg.vsu.survey.model.Answer
import retrofit2.Response
import retrofit2.http.*
interface AnswerAPIService {

    // Сохранение всех ответов пользователя на опрос
    @POST("/surveys/api/answer/")
    suspend fun submitAnswers(
        @Header("Authorization") token: String,
        @Query("user") userId: Int,
        @Query("survey") surveyId: Int,
        @Body answers: List<Answer>
    ): Response<Map<String, String>>

    // Удаление всех ответов пользователя на опрос
    @DELETE("/surveys/api/answer/")
    suspend fun deleteAnswers(
        @Header("Authorization") token: String,
        @Query("user") userId: Int,
        @Query("survey") surveyId: Int
    ): Response<Map<String, String>>
}

