package cg.vsu.survey.data.question

import cg.vsu.survey.model.Question
import retrofit2.Response
import retrofit2.http.*
interface QuestionAPIService {

    // Создание нового вопроса
    @POST("/surveys/api/questions/")
    suspend fun createQuestion(
        @Header("Authorization") token: String,
        @Body question: Question
    ): Response<Question>

    // Получение, обновление или удаление вопроса по ID
    @GET("/surveys/api/questions/{id}/")
    suspend fun getQuestion(
        @Header("Authorization") token: String,
        @Path("id") questionId: Int
    ): Response<Question>

    @PUT("/surveys/api/questions/{id}/")
    suspend fun updateQuestion(
        @Header("Authorization") token: String,
        @Path("id") questionId: Int,
        @Body question: Question
    ): Response<Question>

    @DELETE("/surveys/api/questions/{id}/")
    suspend fun deleteQuestion(
        @Header("Authorization") token: String,
        @Path("id") questionId: Int
    ): Response<Map<String, String>>

    // Получение всех вопросов для определенного опроса
    @GET("/surveys/api/questions/survey/{surveyId}/")
    suspend fun getQuestionsBySurvey(
        @Header("Authorization") token: String,
        @Path("surveyId") surveyId: Int
    ): Response<List<Question>>
}
