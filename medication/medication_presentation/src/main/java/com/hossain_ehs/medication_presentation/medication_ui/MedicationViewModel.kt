package com.hossain_ehs.medication_presentation.medication_ui

import androidx.lifecycle.*
import com.hossain_ehs.core.R
import com.hossain_ehs.core.util.MedicationUiEvents
import com.hossain_ehs.core.util.UiText
import com.hossain_ehs.core_ui.util.Constants.ADD_MEDICINE_RESULT_OK
import com.hossain_ehs.core_ui.util.Constants.EDIT_MEDICINE_RESULT_OK
import com.hossain_ehs.medication_domain.use_case.MedicationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
     val medicationUseCase: MedicationUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val userId = savedStateHandle.get<Int>("userId")
    val state = MedicationState(savedStateHandle)

    private val _medicationChannel = Channel<MedicationUiEvents>()
    val medicationChannel = _medicationChannel.receiveAsFlow()

    init {
        getAllMedication()
        getUserName(userId!!)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onEvent(event: MedicationEvents) {
        when (event) {
            is MedicationEvents.OnAddMedicationClicked -> {
                viewModelScope.launch {
                    _medicationChannel.send(MedicationUiEvents.NavigateToAddNewMedication)
                }
            }
            is MedicationEvents.OnDeleteMedicationClicked -> {
                state.updateDeletedMedication(event.medication)

                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        state.updateDeletedScheduleList(
                            state.deletedMedicationState?.medicationId?.let {
                                medicationUseCase.getScheduleByMedicationIdUseCase(
                                    it
                                )
                            }
                        )
                        for (schedule in state.deletedScheduleListState) {
                            medicationUseCase.deleteScheduleUseCase(schedule)
                        }
                    }
                    medicationUseCase.deleteMedicationUseCase(event.medication)
                    _medicationChannel.send(
                        MedicationUiEvents.ShowUndoDeleteMedicationMessage
                    )
                }
            }
            is MedicationEvents.OnEditMedicationClicked -> {
                viewModelScope.launch {
                    _medicationChannel.send(
                        MedicationUiEvents
                            .NavigateToEditMedication(event.medication.medicationId)
                    )
                }
            }
            is MedicationEvents.OnResultReceived -> {
                viewModelScope.launch {
                    when (event.result) {
                        ADD_MEDICINE_RESULT_OK -> _medicationChannel
                            .send(
                                MedicationUiEvents.ShowMessage(
                                    UiText.ResourceString(R.string.new_medicine_saved_successfully)
                                )
                            )
                        EDIT_MEDICINE_RESULT_OK -> _medicationChannel
                            .send(
                                MedicationUiEvents.ShowMessage(
                                    UiText.ResourceString(R.string.medication_updated_successfully)
                                )
                            )
                    }
                }
            }
            is MedicationEvents.OnUndoDeleteMedicationClicked -> {
                viewModelScope.launch {
                    for (schedule in state.deletedScheduleListState){
                        println(schedule.scheduleId)
                        medicationUseCase.insertScheduleUseCase(schedule)
                    }
                    withContext(viewModelScope.coroutineContext) {
                        medicationUseCase.insertMedicationUseCase(state.deletedMedicationState!!)
                    }
                    state.updateDeletedMedication(
                        deletedMedication = null
                    )
                    state.updateDeletedScheduleList(
                        deletedScheduleList = emptyList()
                    )
                }

            }
            is MedicationEvents.SearchMedicationByMedicineName -> {
                state.updateMedicationSearchQuery(event.query)
                state.updateMedications(
                    state.medicationSearchQueryState.asFlow().flatMapLatest {
                        medicationUseCase.searchMedicationsByNameUseCase(it, userId!!)
                    }
                )

            }
            is MedicationEvents.SearchMedicationByDoctorName -> {
                state.updateMedicationSearchQuery(event.query)
                state.updateMedications(
                    state.medicationSearchQueryState.asFlow().flatMapLatest {
                        medicationUseCase.searchMedicationsByDoctorNameUseCase(it, userId!!)
                    }
                )
            }
            is MedicationEvents.SearchMedicationByNote -> {
                state.updateMedicationSearchQuery(event.query)
                state.updateMedications(
                    state.medicationSearchQueryState.asFlow().flatMapLatest {
                        medicationUseCase.searchMedicationsByNoteUseCase(it, userId!!)
                    }
                )
            }
        }
    }

    private fun getAllMedication() {
        state.updateMedications(
            medicationUseCase
                .getAllMedicationUseCase(userId!!)
        )
    }

    private fun getUserName(userId: Int) {
        viewModelScope.launch {
            medicationUseCase.getUserNameUseCase(userId).collect {
                state.updateUserName(it)
            }
        }

    }

}