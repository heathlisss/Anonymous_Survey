package cg.vsu.survey.model

import java.time.LocalDate

data class Survey(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate? = null,
    val active: Boolean = true,
    val admins: List<Int>? = null,
    val numberOfRespondents: Int =  0
)