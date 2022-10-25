package com.android.personallifelessons.presenter.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.User
import com.android.personallifelessons.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val userId: String
): ViewModel() {

    private val _user = MutableStateFlow<User?>(null)

    private val _username = MutableStateFlow("")
    val username get() = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email get() = _email.asStateFlow()

    private val _photoUrl = MutableStateFlow<String?>(null)
    val photoUrl get() = _photoUrl.asStateFlow()

    private val _uiState = MutableStateFlow<Outcome<String>>(Outcome.Loading())
    val uiState get() = _uiState.asStateFlow()

    init{
        viewModelScope.launch{
            _user.collectLatest { user->
                user?.let{
                    _username.value = it.username
                    _email.value = it.email
                    _photoUrl.value = it.photo
                }
            }
        }
    }

    fun refresh(){
        viewModelScope.launch{
            when(val result = profileRepository.getCurrentUserInfo(userId = userId).first()){
                is Outcome.Error -> _uiState.value = Outcome.Error(result.message!!)
                is Outcome.Loading -> _uiState.value = Outcome.Loading()
                is Outcome.Success -> {
                    _user.value = result.data!!
                    _uiState.value = Outcome.Success("Successfully loaded profile")
                }
            }
        }
    }

    fun setUsername(username: String) {
        _username.value = username
    }
    fun setEmail(email: String){
        _email.value = email
    }
    fun setPhotoUrl(photoUrl: String){
        _photoUrl.value = photoUrl
    }

}