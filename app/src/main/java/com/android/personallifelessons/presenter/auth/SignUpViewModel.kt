package com.android.personallifelessons.presenter.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.SignUpRequest
import com.android.personallifelessons.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val userRepo: UserRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<Outcome<String>?>(null)
    val uiState get() = _uiState.asStateFlow()

    private val _username = MutableStateFlow("")
    val username get() = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email get() = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password get() = _password.asStateFlow()

    fun setUsername(username: String){_username.value = username}
    fun setEmail(email: String){_email.value = email}
    fun setPassword(password: String){_password.value = password}

    fun signUp(){
        viewModelScope.launch{
            _uiState.value = Outcome.Loading
            val signUpRequest = SignUpRequest(
                username = username.value.trim(),
                email = email.value.trim(),
                password = password.value.trim(),
                photo = ""
            )
            _uiState.value = userRepo.signUp(signUpRequest)
        }
    }
}