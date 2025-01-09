package cg.vsu.survey.model

import java.time.LocalDate

data class Survey(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val start_date: String = LocalDate.now().toString(),
    val end_date: String? = "",
    val active: Boolean = true,
    val admins: List<Int>? = null,
    val numberOfRespondents: Int =  0
)