package cg.vsu.survey.model

data class User(
    val id: Int? = null,
    val username: String = "",
    val email: String = "",
    val admin: Boolean = false,
    val answered_surveys: List<Int>? = null,
    val created_surveys: List<Int>? = null,
)