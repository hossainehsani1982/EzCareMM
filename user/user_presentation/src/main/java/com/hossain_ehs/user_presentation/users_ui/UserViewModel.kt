package com.hossain_ehs.user_presentation.users_ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossain_ehs.core.util.UiText
import com.hossain_ehs.core_ui.util.Constants.ADD_USER_RESULT_OK
import com.hossain_ehs.core.R
import com.hossain_ehs.core.util.UserUiEvents
import com.hossain_ehs.core_ui.util.Constants.EDIT_USER_RESULT_OK
import com.hossain_ehs.user_domain.model.User
import com.hossain_ehs.user_domain.use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    var state by mutableStateOf(UsersState())
        private set

    private val _userChannel = Channel<UserUiEvents>()
    val userChannel = _userChannel.receiveAsFlow()

    init {
        getAllUsers()
    }


    fun onEvent(event: UserEvents) {
        when (event) {

            is UserEvents.OnAddUserClicked -> {
                viewModelScope.launch {
                    _userChannel.send(UserUiEvents.NavigateToAddNewUser)
                }
            }
            is UserEvents.OnDeleteUserClicked -> {
                viewModelScope.launch {
                    state = state.copy(deletedUser = event.user)
                    userUseCases.deleteUserUseCase(event.user)
                    _userChannel.send(UserUiEvents.ShowUndoDeleteUserMessage)
                }
            }
            is UserEvents.OnEditUserClicked -> {
                viewModelScope.launch {
                    _userChannel.send(UserUiEvents.NavigateToEditUser(event.user.userId!!))
                }
            }
            is UserEvents.OnResultReceived -> {
                viewModelScope.launch {
                    when (event.result) {
                        ADD_USER_RESULT_OK -> _userChannel
                            .send(
                                UserUiEvents
                                    .ShowMessage(
                                        UiText
                                            .ResourceString(R.string.addUserSuccessMessage)
                                    )
                            )
                        EDIT_USER_RESULT_OK -> _userChannel
                            .send(
                                UserUiEvents
                                    .ShowMessage(
                                        UiText
                                            .ResourceString(R.string.editUserSuccessMessage)
                                    )
                            )
                    }
                }
            }
            is UserEvents.OnUndoDeleteUserClicked -> {
                viewModelScope.launch {
                    userUseCases.insertUserUseCase(
                        User(
                        fullName = state.deletedUser!!.fullName,
                        age = state.deletedUser!!.age,
                        tel = state.deletedUser!!.tel,
                        address = state.deletedUser!!.address,
                        userImageUri = state.deletedUser!!.userImageUri,
                        emergencyNumber = state.deletedUser!!.emergencyNumber,
                        helpType = state.deletedUser!!.helpType,)
                    )
                    state = state.copy(deletedUser = null)
                }
            }
            is UserEvents.OnToMedicationClicked -> {
                viewModelScope.launch {
                    _userChannel.send(UserUiEvents.NavigateToMedication(event.user.userId!!))
                }
            }
        }
    }

    private fun getAllUsers() {
        state = state.copy(users = userUseCases.getAllUserUseCase())

    }
}