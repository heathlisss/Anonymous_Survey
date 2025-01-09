package cg.vsu.survey.network.option

import cg.vsu.survey.utils.DEFAULT_JWT
import cg.vsu.survey.app.RetrofitInstance
import cg.vsu.survey.model.Option

object OptionRestRepository {
    private val api: OptionAPIService

    init {
        val retrofit = RetrofitInstance.instance
        api = retrofit.create(OptionAPIService::class.java)
    }

    // Создание нового варианта ответа
    suspend fun createOption(
        option: Option,
        token: String = DEFAULT_JWT
    ): Option? {
        val response = api.createOption("Bearer $token", option)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Получение варианта ответа по ID
    suspend fun getOption(
        optionId: Int,
        token: String = DEFAULT_JWT
    ): Option? {
        val response = api.getOption("Bearer $token", optionId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Обновление варианта ответа по ID
    suspend fun updateOption(
        optionId: Int,
        option: Option,
        token: String = DEFAULT_JWT
    ): Option? {
        val response = api.updateOption("Bearer $token", optionId, option)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }

    // Удаление варианта ответа по ID
    suspend fun deleteOption(
        optionId: Int,
        token: String = DEFAULT_JWT
    ): String? {
        val response = api.deleteOption("Bearer $token", optionId)
        return if (response.isSuccessful) {
            response.body()?.get("message")
        } else {
            // Обработка ошибок
            null
        }
    }

    // Получение всех вариантов ответа для определенного вопроса
    suspend fun getOptionsByQuestion(
        questionId: Int,
        token: String = DEFAULT_JWT
    ): List<Option>? {
        val response = api.getOptionsByQuestion("Bearer $token", questionId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Обработка ошибок
            null
        }
    }
}
