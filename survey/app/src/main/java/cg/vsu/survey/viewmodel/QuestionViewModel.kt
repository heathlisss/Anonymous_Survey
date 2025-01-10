package cg.vsu.survey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.network.question.QuestionRestRepository
import cg.vsu.survey.model.Question
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryQuestion = QuestionRestRepository
    private val _createdQuestion = MutableLiveData<Question?>()
    val createdQuestion: LiveData<Question?> get() = _createdQuestion
    private val loginViewModel = LoginViewModel(application)

    fun saveQuestion(question: Question, callback: (Question?) -> Unit) {
        viewModelScope.launch {
            val token = loginViewModel.getToken()
            if (token != null) {
                // Создаем вопрос и передаем токен
                val result = repositoryQuestion.createQuestion(question, token)
                _createdQuestion.value = result
                callback(result)
            } else {
                callback(null)
            }
        }
    }

    fun clearCreatedQuestion() {
        _createdQuestion.value = null
    }
}

