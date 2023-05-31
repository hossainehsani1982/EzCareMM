package com.hossain_ehs.user_presentation.add_edit_user_ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossain_ehs.core.util.AddEditUserUiEvents
import com.hossain_ehs.core.util.UiText
import com.hossain_ehs.core_ui.util.Constants.ADD_USER_RESULT_OK
import com.hossain_ehs.core.R
import com.hossain_ehs.core.util.Mode
import com.hossain_ehs.core_ui.util.Constants.EDIT_USER_RESULT_OK
import com.hossain_ehs.user_domain.model.User
import com.hossain_ehs.user_domain.use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditUserViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var state by mutableStateOf(AddEditUserState())
        private set

    private val id = savedStateHandle.get<Int>("userId")

    private val _addEditUserChannel = Channel<AddEditUserUiEvents>()
    val addEditUserChannel = _addEditUserChannel.receiveAsFlow()

    init {
        if (id != 0) {
            state = state.copy(
                userId = id!!,
                mode = Mode.EDIT
            )
            getUser(state.userId)

        } else {
            state = state.copy(
                mode = Mode.ADD
            )
        }
    }

    fun onEvent(events: AddEditUserEvents) {
        when (events) {

            is AddEditUserEvents.OnSaveUserClicked -> {
                checkEntries()
                if (state.isDataOk) {
                    viewModelScope.launch {
                        userUseCases.insertUserUseCase(
                            User(
                                fullName = "${state.userFirstName} ${state.userLastName}",
                                age = state.userAge,
                                tel = state.userTel,
                                address = state.userAddress,
                                userImageUri = state.userImageUri,
                                emergencyNumber = state.userEmergencyNumber,
                                helpType = state.userHelpType,
                            )
                        )
                        _addEditUserChannel.send(
                            AddEditUserUiEvents
                                .NavigateWithResult(ADD_USER_RESULT_OK)
                        )
                    }
                }
            }
            is AddEditUserEvents.OnUpdateUserClicked -> {
                checkEntries()
                if (state.isDataOk) {
                    state = state.copy(
                        userToEdit = User(
                            userId = state.userId,
                            fullName = "${state.userFirstName} ${state.userLastName}",
                            age = state.userAge,
                            tel = state.userTel,
                            address = state.userAddress,
                            userImageUri = state.userImageUri,
                            emergencyNumber = state.userEmergencyNumber,
                            helpType = state.userHelpType,
                        )
                    )
                    viewModelScope.launch {
                        userUseCases.insertUserUseCase(
                            state.userToEdit!!
                        )
                        _addEditUserChannel.send(
                            AddEditUserUiEvents
                                .NavigateWithResult(EDIT_USER_RESULT_OK)
                        )
                    }
                }
            }
            is AddEditUserEvents.OnHelpTypeChanged -> {
                state = state.copy(
                    userHelpType = events.helpType
                )
            }
            is AddEditUserEvents.OnUserImageUriChanged -> {
                state = state.copy(
                    userImageUri = events.userImageUri
                )
            }
            is AddEditUserEvents.OnUserAddressChanged -> {
                state = state.copy(
                    userAddress = events.userAddress
                )
            }
            is AddEditUserEvents.OnUserAgeChanged -> {
                state = state.copy(
                    userAge = events.userAge
                )
            }
            is AddEditUserEvents.OnUserEmergencyNumberChanged -> {
                state = state.copy(
                    userEmergencyNumber = events.userEmergencyNumber
                )
            }
            is AddEditUserEvents.OnUserFirstNameChanged -> {
                state = state.copy(
                    userFirstName = events.userFirstName
                )
            }
            is AddEditUserEvents.OnUserLastNameChanged -> {
                state = state.copy(
                    userLastName = events.userLastName
                )
            }
            is AddEditUserEvents.OnUserTelChanged -> {
                state = state.copy(
                    userTel = events.userTel
                )
            }

        }
    }

    private fun checkEntries() {
        if (state.userFirstName.isEmpty()) {
            showErrorMessage(UiText.ResourceString(R.string.user_name_filed_is_empty))
            state = state.copy(
                isDataOk = false
            )
        }
        if (state.userLastName.isEmpty()) {
            showErrorMessage(UiText.ResourceString(R.string.user_name_filed_is_empty))
            state = state.copy(
                isDataOk = false
            )
        }

        state = state.copy(
            isDataOk = true
        )

    }


    private fun showErrorMessage(message: UiText) {
        viewModelScope.launch {
            _addEditUserChannel.send(
                AddEditUserUiEvents.ShowError(message)
            )
        }
    }

    private fun getUser(userId: Int) {
        viewModelScope.launch {
            val user = userUseCases.getUserByIdUseCase(userId)
            state = state.copy(
                userFirstName = user.fullName.split(" ")[0],
                userLastName = user.fullName.split(" ")[1],
                userAge = user.age,
                userTel = user.tel,
                userAddress = user.address,
                userImageUri = user.userImageUri,
                userEmergencyNumber = user.emergencyNumber,
                userHelpType = user.helpType,
                userToEdit = user
            )
            _addEditUserChannel.send(AddEditUserUiEvents.LoadUserInfo)
        }
    }


}