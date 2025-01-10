package cg.vsu.survey.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.model.User
import cg.vsu.survey.model.UserRegistration
import cg.vsu.survey.network.User.UserRestRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val USER_ID_KEY = "user_id"
    private val USERNAME_KEY = "username"
    private val TOKEN_KEY = "jwt_token"

    fun loginUser(credentials: UserRegistration) {
        viewModelScope.launch {
            if (credentials.username.isBlank() || credentials.password.isBlank()) {
                _errorMessage.value = "The password or username was not entered"
                return@launch
            }

            try {
                val result = UserRestRepository.loginUser(credentials)
                if (result != null) {
                    val (user, token) = result
                    saveUserData(user, token)
                    _loginSuccess.value = true
                } else {
                    _errorMessage.value = "Invalid username or password"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Connection error: ${e.message}"
            }
        }
    }

    private fun saveUserData(user: User, token: String) {
        with(sharedPreferences.edit()) {
            putInt(USER_ID_KEY, user.id ?: -1)
            putString(USERNAME_KEY, user.username)
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    suspend fun getUserData(): Pair<User, String>? {
        val id = sharedPreferences.getInt(USER_ID_KEY, -1)
        val username = sharedPreferences.getString(USERNAME_KEY, null)
        val token = sharedPreferences.getString(TOKEN_KEY, null)

        if (id == -1 || username == null || token == null) return null

        val user = User(id = id, username = username)
        return Pair(user, token)
    }

    suspend fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearError() {
        _errorMessage.value = null
    }
}



