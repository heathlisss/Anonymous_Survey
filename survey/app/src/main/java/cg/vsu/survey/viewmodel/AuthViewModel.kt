package cg.vsu.survey.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cg.vsu.survey.model.User
import cg.vsu.survey.model.UserAuth
import cg.vsu.survey.network.User.UserRestRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> get() = _signUpSuccess

    private val _userDetails = MutableLiveData<User?>()
    val userDetails: LiveData<User?> get() = _userDetails

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun loginUser(credentials: UserAuth) {
        viewModelScope.launch {
            if (credentials.username.isBlank() || credentials.password.isBlank()) {
                _errorMessage.value = "The password or username was not entered"
                return@launch
            }

            try {
                val result = UserRestRepository.loginUser(credentials)
                if (result != null) {
                    saveUserData(result.first, result.second)
                    _loginSuccess.value = true
                } else {
                    _errorMessage.value = "Invalid username or password"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Connection error: ${e.message}"
            }
        }
    }


    fun registerUser(user: UserAuth, passwordReplay: String) {
        viewModelScope.launch {
            if (!arePasswordsMatching(user.password, passwordReplay)) {
                _errorMessage.value = "Passwords do not match"
                return@launch
            }

            if (user.username.isBlank() || user.password.isBlank()) {
                _errorMessage.value = "All fields are required"
                return@launch
            }

            try {
                val result = UserRestRepository.createUser(user)
                if (result != null) {
                    saveUserData(result.first, result.second)
                    _signUpSuccess.value = true
                } else {
                    _errorMessage.value = "Registration failed"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Connection error: ${e.message}"
            }
        }
    }


    fun getUserById(userId: Int) {
        viewModelScope.launch {
            try {
                val token = getToken()
                if (token == null) {
                    _errorMessage.value = "Token not found"
                    return@launch
                }

                val user = UserRestRepository.getUser(userId, token)
                _userDetails.value = user
            } catch (e: Exception) {
                _errorMessage.value = "Connection error: ${e.message}"
            }
        }
    }

    fun deleteUserById(userId: Int) {
        viewModelScope.launch {

            try {
                val token = getToken()
                if (token == null) {
                    _errorMessage.value = "Token not found"
                    return@launch
                }

                val result = UserRestRepository.deleteUser(userId, token)
                if (result) {
                    _loginSuccess.value = true
                } else {
                    _errorMessage.value = "Failed to delete user"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Connection error: ${e.message}"
            }
        }
    }

    fun updateUserById(userId: Int, user: UserAuth, passwordReplay: String) {
        viewModelScope.launch {
            if (!arePasswordsMatching(user.password, passwordReplay)) {
                _errorMessage.value = "Passwords do not match"
                return@launch
            }
            try {
                val token = getToken()
                if (token == null) {
                    _errorMessage.value = "Token not found"
                    return@launch
                }

                val result = UserRestRepository.updateUser(userId, user, token)
                if (result != null) {
                    loginUser(result)
                    _loginSuccess.value = true
                } else {
                    _errorMessage.value = "Failed to update user"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Connection error: ${e.message}"
            }
        }
    }

    private fun saveUserData(user: User, token: String) {
        with(sharedPreferences.edit()) {
            putInt("id", user.id?.toInt() ?: 0)
            putString("username", user.username)
            putString("email", user.email)
            putBoolean("admin", user.admin)
            putString("answered_surveys", user.answered_surveys?.joinToString(","))
            putString("created_surveys", user.created_surveys?.joinToString(","))
            putString("jwt_token", token)
            apply()
        }
    }

    private fun arePasswordsMatching(password: String, passwordReplay: String): Boolean {
        return password == passwordReplay
    }

    suspend fun getToken(): String? {
        return sharedPreferences.getString("jwt_token", null)
    }
    fun getUsernameFromPrefs(): String? {
        return sharedPreferences.getString("username", null)
    }
    fun getUserIdFromPrefs(): Int? {
        return sharedPreferences.getInt("id", -1)
    }
}