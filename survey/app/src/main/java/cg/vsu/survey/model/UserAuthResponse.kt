package cg.vsu.survey.model

data class UserAuthResponse(
    val id: Int? = null,
    val username: String = "",
    val email: String? = null,
    val admin: Boolean = false,
    val answered_surveys: List<Int>? = null,
    val created_surveys: List<Int>? = null,
    val token: String? = null
)
