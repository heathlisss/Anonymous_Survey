package cg.vsu.survey.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.data.option.OptionRestRepository
import cg.vsu.survey.model.Option
import kotlinx.coroutines.launch

class OptionViewModel : ViewModel() {
    private val repositoryOption = OptionRestRepository

    fun saveOption(option: Option) {
        viewModelScope.launch {
            repositoryOption.createOption(option)
        }
    }
}