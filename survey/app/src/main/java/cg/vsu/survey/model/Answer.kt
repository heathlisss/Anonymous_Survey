package cg.vsu.survey.model

data class Answer(
    val id: Int? = null,
    val question: Int,
    val options: List<Int>
)