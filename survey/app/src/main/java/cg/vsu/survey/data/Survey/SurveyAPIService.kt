package cg.vsu.survey.data.Survey

import cg.vsu.survey.model.Survey
import retrofit2.Response
import retrofit2.http.*
interface SurveyAPIService {
    // Поиск опросов
    @GET("/surveys/api/surveys/all/")
    suspend fun searchSurveys(
        @Header("Authorization") token: String,
        @Query("search") query: String
    ): Response<List<Survey>>

    // Получение всех опросов
    @GET("/surveys/api/surveys/all/")
    suspend fun getAllSurveys(
        @Header("Authorization") token: String
    ): Response<List<Survey>>

    // Создание нового опроса
    @POST("/surveys/api/surveys/")
    suspend fun createSurvey(
        @Header("Authorization") token: String,
        @Body survey: Survey
    ): Response<Survey>

    // Получение опроса по ID
    @GET("/surveys/api/surveys/{id}/")
    suspend fun getSurveyById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Survey>

    // Обновление опроса
    @PUT("/surveys/api/surveys/{id}/")
    suspend fun updateSurvey(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body survey: Survey
    ): Response<Survey>

    // Удаление опроса
    @DELETE("/surveys/api/surveys/{id}/")
    suspend fun deleteSurvey(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Void>

    // Получение опросов пользователя по ID
    @GET("/surveys/api/surveys/user/{id}/")
    suspend fun getUserSurveys(
        @Header("Authorization") token: String,
        @Path("id") userId: Int
    ): Response<List<Survey>>
}

