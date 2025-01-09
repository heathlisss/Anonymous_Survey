package cg.vsu.survey.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.network.question.QuestionRestRepository
import cg.vsu.survey.model.Question
import kotlinx.coroutines.launch
class QuestionViewModel : ViewModel() {
    private val repositoryQuestion = QuestionRestRepository
    private val _createdQuestion = MutableLiveData<Question?>()
    val createdQuestion: LiveData<Question?> get() = _createdQuestion

    fun saveQuestion(question: Question, callback: (Question?) -> Unit) {
        viewModelScope.launch {
            val result = repositoryQuestion.createQuestion(question)
            _createdQuestion.value = result
            callback(result)
        }
    }

    fun clearCreatedQuestion() {
        _createdQuestion.value = null
    }
}
