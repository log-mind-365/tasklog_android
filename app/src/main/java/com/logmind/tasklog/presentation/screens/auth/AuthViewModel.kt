package com.logmind.tasklog.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logmind.tasklog.domain.usecases.GetJwtTokenUseCase
import com.logmind.tasklog.domain.usecases.LoginUseCase
import com.logmind.tasklog.domain.usecases.RegisterUseCase
import com.logmind.tasklog.domain.usecases.SaveJwtTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val saveJwtTokenUseCase: SaveJwtTokenUseCase,
    private val getJwtTokenUseCase: GetJwtTokenUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val token = getJwtTokenUseCase()
            uiState = uiState.copy(isAuthenticated = !token.isNullOrEmpty())
        }
    }

    fun onEmailChanged(email: String) {
        uiState = uiState.copy(email = email, emailError = null)
    }

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(password = password, passwordError = null)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        uiState = uiState.copy(confirmPassword = confirmPassword, confirmPasswordError = null)
    }

    fun toggleAuthMode() {
        uiState = uiState.copy(
            isLoginMode = !uiState.isLoginMode,
            emailError = null,
            passwordError = null,
            confirmPasswordError = null,
            generalError = null
        )
    }

    fun login() {
        if (!validateLoginInput()) return

        uiState = uiState.copy(isLoading = true, generalError = null)

        viewModelScope.launch {
            loginUseCase(uiState.email, uiState.password)
                .onSuccess { authResponse ->
                    saveJwtTokenUseCase(authResponse.accessToken, authResponse.refreshToken)
                    uiState = uiState.copy(
                        isLoading = false,
                        isAuthenticated = true
                    )
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        generalError = error.message ?: "로그인에 실패했습니다."
                    )
                }
        }
    }

    fun register() {
        if (!validateRegisterInput()) return

        uiState = uiState.copy(isLoading = true, generalError = null)

        viewModelScope.launch {
            registerUseCase(uiState.email, uiState.password)
                .onSuccess { authResponse ->
                    saveJwtTokenUseCase(authResponse.accessToken, authResponse.refreshToken)
                    uiState = uiState.copy(
                        isLoading = false,
                        isAuthenticated = true
                    )
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        generalError = error.message ?: "회원가입에 실패했습니다."
                    )
                }
        }
    }

    private fun validateLoginInput(): Boolean {
        var isValid = true
        var newUiState = uiState

        if (uiState.email.isBlank()) {
            newUiState = newUiState.copy(emailError = "이메일을 입력해주세요.")
            isValid = false
        } else if (!isValidEmail(uiState.email)) {
            newUiState = newUiState.copy(emailError = "올바른 이메일 형식이 아닙니다.")
            isValid = false
        }

        if (uiState.password.isBlank()) {
            newUiState = newUiState.copy(passwordError = "비밀번호를 입력해주세요.")
            isValid = false
        }

        uiState = newUiState
        return isValid
    }

    private fun validateRegisterInput(): Boolean {
        var isValid = true
        var newUiState = uiState

        if (uiState.email.isBlank()) {
            newUiState = newUiState.copy(emailError = "이메일을 입력해주세요.")
            isValid = false
        } else if (!isValidEmail(uiState.email)) {
            newUiState = newUiState.copy(emailError = "올바른 이메일 형식이 아닙니다.")
            isValid = false
        }

        if (uiState.password.isBlank()) {
            newUiState = newUiState.copy(passwordError = "비밀번호를 입력해주세요.")
            isValid = false
        } else if (uiState.password.length < 6) {
            newUiState = newUiState.copy(passwordError = "비밀번호는 최소 6자 이상이어야 합니다.")
            isValid = false
        }

        if (uiState.confirmPassword.isBlank()) {
            newUiState = newUiState.copy(confirmPasswordError = "비밀번호 확인을 입력해주세요.")
            isValid = false
        } else if (uiState.password != uiState.confirmPassword) {
            newUiState = newUiState.copy(confirmPasswordError = "비밀번호가 일치하지 않습니다.")
            isValid = false
        }

        uiState = newUiState
        return isValid
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoginMode: Boolean = true,
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val generalError: String? = null
)