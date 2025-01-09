package cg.vsu.survey.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.app.DEFAULT_JWT
import cg.vsu.survey.data.Survey.SurveyRestRepository
import cg.vsu.survey.model.Survey
import kotlinx.coroutines.launch

class SurveyViewModel : ViewModel() {

    private val repository = SurveyRestRepository // Используем объект репозитория
    private val _surveyCreationStatus = MutableLiveData<Survey?>()
    val surveyCreationStatus: LiveData<Survey?> get() = _surveyCreationStatus

    fun saveSurvey(survey: Survey) {
        viewModelScope.launch {
            val createdSurvey = repository.createSurvey(survey)
            _surveyCreationStatus.value = createdSurvey
        }
    }
}

