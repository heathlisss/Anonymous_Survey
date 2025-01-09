package cg.vsu.survey.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.utils.DEFAULT_SURVEYS_WHEN_TO_LOAD
import cg.vsu.survey.network.survey.SurveyRestRepository
import cg.vsu.survey.model.Survey
import kotlinx.coroutines.launch


class SurveyListViewModel(
    private val loadLimit: Int = DEFAULT_SURVEYS_WHEN_TO_LOAD
) : ViewModel() {

    private var loading = false
    private var endOfFeed = false

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
            val newItems = SurveyRestRepository.getAllSurveys()
            if (!newItems.isNullOrEmpty()) {
                addSurveys(newItems)
            } else {
                endOfFeed = true
            }
            loading = false
        }
    }

    fun searchSurveys(query: String) {
        loading = true
        viewModelScope.launch {
            val newItems = SurveyRestRepository.searchSurveys(query)
            _surveys.value = newItems?.toMutableSet() ?: mutableSetOf()
            loading = false
        }
    }

    fun clearSearch() {
        _surveys.value = mutableSetOf()
    }
}
