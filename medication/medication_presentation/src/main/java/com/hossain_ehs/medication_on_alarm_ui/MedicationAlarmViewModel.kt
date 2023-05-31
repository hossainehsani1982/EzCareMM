package com.hossain_ehs.medication_on_alarm_ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossain_ehs.core.domain.shared_preferences.Preferences
import com.hossain_ehs.core.util.MedicationAlarmUiEvents
import com.hossain_ehs.medication_domain.model.alarm.AlarmItem
import com.hossain_ehs.medication_domain.model.alarm.AlarmScheduler
import com.hossain_ehs.medication_domain.model.schedule.ArchivedSchedule
import com.hossain_ehs.medication_domain.use_case.MedicationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class MedicationAlarmViewModel @Inject constructor(
    val medicationUseCases: MedicationUseCases,
    savedStateHandle: SavedStateHandle,
    private val preferences: Preferences,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {


    val state = MedicationAlarmStates(savedStateHandle)
    private val _medicationAlarmEvents = Channel<MedicationAlarmUiEvents>()
    val medicationAlarmEvents = _medicationAlarmEvents.receiveAsFlow()

    init {
        getMedication()
    }

    fun onEvent(event: MedicationAlarmEvents) {
        when (event) {
            is MedicationAlarmEvents.GetCorrespondingUserData -> {
                viewModelScope.launch {
                    getUserData(event.medication.userId)
                }
            }
            MedicationAlarmEvents.OnMedicationNotTakenSwiped -> {

            }
            MedicationAlarmEvents.OnMedicationTakenSwiped -> {

            }
            MedicationAlarmEvents.OnDismissClicked -> {
                state.medicationsState.forEach { med ->
                    viewModelScope.launch {
                        getSchedule(med.medicationId)
                        withContext(Dispatchers.IO) {
                            medicationUseCases.insertArchivedScheduleUseCase(
                                ArchivedSchedule(
                                    scheduleForPerson = med.userName,
                                    scheduleForMedication = med.medicineName,
                                    approximateUsagePeriod = med.approximateUsagePeriod,
                                    medicationId = med.medicationId,
                                    userId = med.userId,
                                    scheduledTime = med.nextSchedule,
                                    hasTaken = true,
                                )
                            )
                        }
                        withContext(Dispatchers.IO) {
                            getSchedule(med.medicationId)
                        }
                        medicationUseCases.insertMedicationUseCase(
                            med.copy(
                                nextSchedule = state.scheduleToDelete!!.nextSchedule,
                            )
                        )
                    }
                }
                viewModelScope.launch {
                    val newSchedule =
                        medicationUseCases.getMedicationsOrdByScheduleACSUseCase()
                            .minByOrNull {
                                it.nextSchedule
                            }!!
                    alarmScheduler.schedule(
                        item = AlarmItem(
                            time = Instant.ofEpochMilli(newSchedule.nextSchedule)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                        )
                    )
                    preferences.setServerStatus(false)
                    _medicationAlarmEvents.send(MedicationAlarmUiEvents.OnSlideComplete)
                }
            }
            MedicationAlarmEvents.OnSnoozeClicked -> {

            }

        }
    }

    private fun getMedication() {
        viewModelScope.launch {
            val medications = medicationUseCases.getMedicationsOrdByScheduleACSUseCase()

            withContext(Dispatchers.Default) {
                state.updateMedications(medications.stream().filter { m ->
                    m.nextSchedule
                    -
                    medications.first().nextSchedule <= 120000
                }.collect(Collectors.toList()))
            }

            state.updateMedicationListSize(state.medicationsState.size)

            _medicationAlarmEvents.send(MedicationAlarmUiEvents.OnDataReady)
        }
    }

    private suspend fun getUserData(userId: Int) {
        withContext(Dispatchers.IO) {
            viewModelScope.launch {
                medicationUseCases.getUserNameUseCase(userId).collect {
                    state.updateUserName(it)
                }
            }
        }
        withContext(Dispatchers.IO) {
            state.updateUserImageUri(
                medicationUseCases.getUserImageUriUseCase(userId)
            )
        }
        _medicationAlarmEvents.send(
            MedicationAlarmUiEvents
                .OnUserDataReady(state.userImageUriState, state.userNameState)
        )

    }

    private suspend fun getSchedule(medicationId: Int) {

        withContext(Dispatchers.IO) {
            val schedule = medicationUseCases
                .getScheduleByMedicationIdUseCase(medicationId)
                .sortedBy {
                    it.nextSchedule
                }
            state.scheduleToDelete(
                schedule.first()
            )
        }
        state.scheduleToDelete?.let {
            medicationUseCases.deleteScheduleUseCase(it)
        }



}

}