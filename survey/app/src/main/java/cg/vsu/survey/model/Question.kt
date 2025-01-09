package cg.vsu.survey.model

data class Question(
    val id: Int? = null,
    val survey: Int, // ID опроса
    val text: String = "",
    val options: MutableList<Option> = mutableListOf()
)
