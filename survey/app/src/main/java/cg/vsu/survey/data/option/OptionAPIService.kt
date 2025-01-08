package cg.vsu.survey.data.option

import cg.vsu.survey.model.Option
import retrofit2.Response
import retrofit2.http.*
interface OptionAPIService {

    // Создание нового варианта ответа
    @POST("/surveys/api/options/")
    suspend fun createOption(
        @Header("Authorization") token: String,
        @Body option: Option
    ): Response<Option>

    // Получение, обновление или удаление варианта ответа по ID
    @GET("/surveys/api/options/{id}/")
    suspend fun getOption(
        @Header("Authorization") token: String,
        @Path("id") optionId: Int
    ): Response<Option>

    @PUT("/surveys/api/options/{id}/")
    suspend fun updateOption(
        @Header("Authorization") token: String,
        @Path("id") optionId: Int,
        @Body option: Option
    ): Response<Option>

    @DELETE("/surveys/api/options/{id}/")
    suspend fun deleteOption(
        @Header("Authorization") token: String,
        @Path("id") optionId: Int
    ): Response<Map<String, String>>

    // Получение всех вариантов ответа для определенного вопроса
    @GET("/surveys/api/options/question/{questionId}/")
    suspend fun getOptionsByQuestion(
        @Header("Authorization") token: String,
        @Path("questionId") questionId: Int
    ): Response<List<Option>>
}
