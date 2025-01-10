package cg.vsu.survey.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.network.option.OptionRestRepository
import cg.vsu.survey.model.Option
import kotlinx.coroutines.launch

class OptionViewModel : ViewModel() {
    private val repositoryOption = OptionRestRepository

    fun saveOption(option: Option) {
        Log.d("начинаем сохранять", ": $option")
        viewModelScope.launch {
            repositoryOption.createOption(option)
        }
        Log.d("успешно", ": $option")
    }
}