package cg.vsu.survey.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.data.question.QuestionRestRepository
import cg.vsu.survey.model.Question
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    private val repositoryQuestion = QuestionRestRepository

    fun saveQuestion(question: Question) {
        viewModelScope.launch {
            repositoryQuestion.createQuestion(question)
        }
    }
}