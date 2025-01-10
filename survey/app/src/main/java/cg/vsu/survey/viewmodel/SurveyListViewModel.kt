package cg.vsu.survey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.utils.DEFAULT_SURVEYS_WHEN_TO_LOAD
import cg.vsu.survey.network.survey.SurveyRestRepository
import cg.vsu.survey.model.Survey
import kotlinx.coroutines.launch


class SurveyListViewModel(application: Application) : AndroidViewModel(application) {

    private val loadLimit: Int = DEFAULT_SURVEYS_WHEN_TO_LOAD
    private var loading = false
    private var endOfFeed = false
    private val loginViewModel = AuthViewModel(application)

    fun isLoading(): Boolean = loading
    fun isEnded(): Boolean = endOfFeed

    private val _surveys = MutableLiveData<MutableSet<Survey>>()
    val surveys: LiveData<MutableSet<Survey>> get() = _surveys

    init {
        _surveys.value = mutableSetOf()
    }

    private fun addSurveys(newSurveys: List<Survey>) {
        val currentSet = _surveys.value ?: mutableSetOf()
        currentSet.addAll(newSurveys)
        _surveys.value = currentSet
    }

    fun loadData() {
        if (endOfFeed || (surveys.value?.size ?: 0) >= loadLimit) {
            loading = false
            return
        }
        loading = true

        viewModelScope.launch {
            val token = loginViewModel.getToken()
            if (token != null) {
                val newItems = SurveyRestRepository.getAllSurveys(token)
                if (!newItems.isNullOrEmpty()) {
                    addSurveys(newItems)
                } else {
                    endOfFeed = true
                }
                loading = false
            }
        }
    }

    fun searchSurveys(query: String) {
        loading = true
        viewModelScope.launch {
            val token = loginViewModel.getToken()
            if (token != null) {
                val newItems = SurveyRestRepository.searchSurveys(query, token)
                _surveys.value = newItems?.toMutableSet() ?: mutableSetOf()
                loading = false
            }
        }
    }

    fun clearSearch() {
        _surveys.value = mutableSetOf()
    }
}
