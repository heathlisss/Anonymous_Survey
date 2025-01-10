package cg.vsu.survey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.network.survey.SurveyRestRepository
import cg.vsu.survey.model.Survey
import kotlinx.coroutines.launch

class SurveyViewModel(application: Application) : AndroidViewModel(application) {
    private val repositorySurvey = SurveyRestRepository
    private val _surveyCreationStatus = MutableLiveData<Survey?>()
    private val loginViewModel = LoginViewModel(application)
    val surveyCreationStatus: LiveData<Survey?> get() = _surveyCreationStatus

    fun saveSurvey(survey: Survey, callback: (Survey?) -> Unit) {
        viewModelScope.launch {
            val token = loginViewModel.getToken()
            if (token != null) {
            val createdSurvey = repositorySurvey.createSurvey(survey, token)
            _surveyCreationStatus.value = createdSurvey
            callback(createdSurvey)}
        }
    }
}

