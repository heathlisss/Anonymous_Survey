package cg.vsu.survey.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cg.vsu.survey.app.DEFAULT_SURVEYS_WHEN_TO_LOAD
import cg.vsu.survey.data.Survey.SurveyRestRepository
import cg.vsu.survey.model.Survey
import kotlinx.coroutines.runBlocking


class SurveyListViewModel(
    private val loadLimit: Int = DEFAULT_SURVEYS_WHEN_TO_LOAD
) : ViewModel() {

    private var loading = false
    private var endOfFeed = false

    fun isLoading(): Boolean = loading
    fun isEnded(): Boolean = endOfFeed

    private val _surveys = MutableLiveData<MutableList<Survey>>()
    val surveys: LiveData<MutableList<Survey>> get() = _surveys

    init {
        _surveys.value = mutableListOf()
    }

    private fun addSurveys(newSurveys: List<Survey>) {
        val currentList = _surveys.value ?: mutableListOf()
        currentList.addAll(newSurveys)
        _surveys.value = currentList
    }

    fun loadData() {
        if (endOfFeed || (surveys.value?.size ?: 0) >= loadLimit) {
            loading = false
            return
        }
        loading = true

        runBlocking {
            val newItems = SurveyRestRepository.getAllSurveys()
            if (!newItems.isNullOrEmpty()) {
                addSurveys(newItems)
            } else {
                endOfFeed = true
            }
            loading = false
        }
    }


}