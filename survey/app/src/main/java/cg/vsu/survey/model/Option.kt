package cg.vsu.survey.model

data class Option(
    val id: Int? = null,
    val question: Int, // ID вопроса
    val text: String = "",
    val selectedCount: Int = 0 // Количество выборов
)
