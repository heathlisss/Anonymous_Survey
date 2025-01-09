package cg.vsu.survey.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.network.survey.SurveyRestRepository
import cg.vsu.survey.model.Survey
import kotlinx.coroutines.launch

class SurveyViewModel : ViewModel() {
    private val repositorySurvey = SurveyRestRepository
    private val _surveyCreationStatus = MutableLiveData<Survey?>()
    val surveyCreationStatus: LiveData<Survey?> get() = _surveyCreationStatus

    fun saveSurvey(survey: Survey, callback: (Survey?) -> Unit) {
        viewModelScope.launch {
            val createdSurvey = repositorySurvey.createSurvey(survey)
            _surveyCreationStatus.value = createdSurvey
            callback(createdSurvey)
        }
    }
}

