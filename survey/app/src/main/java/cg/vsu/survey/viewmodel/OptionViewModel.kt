package cg.vsu.survey.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.network.option.OptionRestRepository
import cg.vsu.survey.model.Option
import kotlinx.coroutines.launch

class OptionViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryOption = OptionRestRepository
    private val loginViewModel = LoginViewModel(application)

    fun saveOption(option: Option) {
        Log.d("начинаем сохранять", ": $option")
        viewModelScope.launch {
            val token = loginViewModel.getToken()
            if (token != null) {
                repositoryOption.createOption(option, token)
            }
        }
        Log.d("успешно", ": $option")
    }
}