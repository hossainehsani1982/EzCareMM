package com.hossain_ehs.medication_presentation.add_edit_medication_ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossain_ehs.core.util.AddEditMedicationUiEvents
import com.hossain_ehs.core.util.Mode
import com.hossain_ehs.core_ui.util.Constants.ADD_MEDICINE_RESULT_OK
import com.hossain_ehs.core_ui.util.Constants.EDIT_MEDICINE_RESULT_OK
import com.hossain_ehs.medication_domain.model.medication.DateParts
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.use_case.MedicationUseCases
import com.hossain_ehs.core.R
import com.hossain_ehs.core.util.UiText
import com.hossain_ehs.medication_domain.model.alarm.AlarmItem
import com.hossain_ehs.medication_domain.model.alarm.AlarmScheduler
import com.hossain_ehs.medication_domain.model.schedule.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.Instant
import java.time.ZoneId
import java.util.*
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class AddEditMedicationViewModel @Inject constructor(
    private val medicationUseCase: MedicationUseCases,
    savedStateHandle: SavedStateHandle,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

    private var medicationId = savedStateHandle.get<Int>("medId") ?: 0
    private val userName = savedStateHandle.get<String>("userName") ?: ""
    private val userId = savedStateHandle.get<Int>("userId") ?: 0

    var state = AddEditMedicationStates(
        savedStateHandle = savedStateHandle
    )

    private val _addEditMedicationChannel = Channel<AddEditMedicationUiEvents>()
    val addEditMedicationChannel = _addEditMedicationChannel.receiveAsFlow()


    init {
        state.updateUserNameState(userName)
        state.updateLanguageState(Locale.getDefault().displayLanguage)
        state.updateYearsStates(
            medicationUseCase.getYearsUseCase(
                language = state.languageState!!
            )
        )
        if (medicationId != 0) {
            state.updateMedicationIdState(medicationId)
            state.updateModeState(Mode.EDIT)

        } else {
            state.updateModeState(Mode.ADD)
        }
        setMonths()
    }

    fun onEvent(event: AddEditMedicationEvents) {
        when (event) {
            is AddEditMedicationEvents.SetMonths -> {
                state.updateMonthsState(
                    event.months
                )
            }
            /**
             * medication text fields change
             * **/
            is AddEditMedicationEvents.OnMedicationNameChanged -> {
                state.updateMedicineNameState(event.medicationName)
            }
            is AddEditMedicationEvents.OnMedicationNotesChanged -> {
                state.updateMedicationNotesState(event.medicationNotes)
            }
            is AddEditMedicationEvents.OnDoctorNameChanged -> {
                state.updateDoctorNameState(event.doctorName)
            }
            is AddEditMedicationEvents.OnMedicationImageUriChanged -> {
                state.updateMedicationImageUriState(event.medicationImageUri)
            }
            is AddEditMedicationEvents.OnMedicationDosageChanged -> {
                state.updateMedicationDosageState(event.medicationDosage)
            }
            is AddEditMedicationEvents.OnMedicationApproximateUsagePeriodChanged -> {
                state.updateApproximateUsagePeriodState(event.approximateUsagePeriod)
            }

            /**
            * add/update medication
             * **/
            is AddEditMedicationEvents.AddMedication -> {
                calculateNextSchedule(state.startingScheduleState)
                viewModelScope.launch {
                    if (withContext(Dispatchers.Default) {
                            isEntriesOk()
                        }
                    ) {
                        if (withContext(Dispatchers.IO) {
                                checkForDuplicates()
                            }
                        ) {
                            insertNewMed()
                        }
                    }
                }
            }
            is AddEditMedicationEvents.UpdateMedication -> {
                viewModelScope.launch {
                    if (withContext(Dispatchers.Default) {
                            isEntriesOk()
                        }) {
                        if (withContext(Dispatchers.IO) {
                                checkForDuplicates()
                            }) {
                            updateMedication()
                        }
                    }
                }
            }


            is AddEditMedicationEvents.OnMedicationNextScheduleChanged -> {
                state.updateNextScheduleState(event.nextSchedule)
            }
            is AddEditMedicationEvents.OnMedicationStartingScheduleChanged -> {
                state.updateStartingScheduleState(event.startingSchedule)
            }
            is AddEditMedicationEvents.OnMedicationStatusChanged -> {
                state.updateMedicationStatusState(event.medicationStatus)
            }
            is AddEditMedicationEvents.OnMedicationTypeChanged -> {
                state.updateMedicationTypeState(event.medicationType)
            }
            /** time Pickers -> **/
            /**Year**/
            is AddEditMedicationEvents.SetYearPickerValue -> {
                state.updateStartScheduleYearState(event.year)
                state.updateDaysInMonthMaxValState(
                    medicationUseCase.getDaysByMonthAndYearUseCase(
                        language = state.languageState!!,
                        month = state.startScheduleMonthState,
                        year = state.startScheduleYearState
                    )
                )
            }
            is AddEditMedicationEvents.SetInitialYearPickerValue -> {
                state.updateStartScheduleYearState(
                    medicationUseCase.setYearUseCase(
                        language = state.languageState!!
                    ).toInt()
                )
            }
            /**Month**/
            is AddEditMedicationEvents.SetMonthPickerValue -> {
                state.updateStartScheduleMonthState(event.month)
                state.updateDaysInMonthMaxValState(
                    medicationUseCase.getDaysByMonthAndYearUseCase(
                        language = state.languageState!!,
                        month = state.startScheduleMonthState,
                        year = state.startScheduleYearState
                    )
                )
            }
            /**Month initial (current month)**/
            is AddEditMedicationEvents.SetInitialMonthPickerValue -> {
                state.updateStartScheduleMonthState(
                    medicationUseCase.setMonthUseCase(
                        language = state.languageState!!
                    ).toInt() + 1
                )
            }
            /**Day ->**/
            /**Day picker changed**/
            is AddEditMedicationEvents.SetDayPickerValue -> {
                state.updateStartScheduleDayState(event.day)
            }
            /**Days Array<String>**/
            is AddEditMedicationEvents.SetDaysInMonthPickerValue -> {
                state.updateDaysInMonthMaxValState(
                    medicationUseCase.getDaysUseCase(
                        language = state.languageState!!,
                        month = state.startScheduleMonthState
                    )
                )
            }
            /**Day initial (current day)**/
            AddEditMedicationEvents.SetInitialDayInMonthPickerValue -> {
                state.updateStartScheduleDayState(
                    medicationUseCase.setDayUseCase(
                        language = state.languageState!!
                    ).toInt()
                )
            }
            /**Hour -> **/
            /**Hour picker changed**/
            is AddEditMedicationEvents.SetHourPickerValue -> {
                state.updateStartScheduleHourOfDay(event.hour)
            }
            /**Hours Array<String>**/
            is AddEditMedicationEvents.SetHours -> {
                state.updateStartScheduleHoursState(
                    medicationUseCase.getHoursUseCase(
                        language = state.languageState!!
                    )
                )
            }
            /**Hour initial (current hour)**/
            is AddEditMedicationEvents.SetInitialHourPickerValue -> {
                state.updateStartScheduleHourOfDay(
                    medicationUseCase.setHourUseCase(
                        language = state.languageState!!
                    ).toInt()
                )
            }
            /**Minute -> **/
            /**Minute picker changed**/
            is AddEditMedicationEvents.SetMinutePickerValue -> {
                state.updateStartScheduleMinuteOfDay(event.minute)
            }
            /**Minute Array<String>**/
            is AddEditMedicationEvents.SetMinutes -> {
                state.updateStartScheduleMinutesState(
                    medicationUseCase.getMinutesUseCase(
                        language = state.languageState!!
                    )
                )
            }
            /**Minute initial (current minute)**/
            is AddEditMedicationEvents.SetInitialMinutePickerValue -> {
                state.updateStartScheduleMinuteOfDay(
                    medicationUseCase.setMinuteUseCase(
                        language = state.languageState!!
                    ).toInt() + 1
                )
            }
            /**Button done clicked**/
            is AddEditMedicationEvents.OnButtonDoneClicked -> {
                state.updateStartingScheduleState(
                    medicationUseCase.calculateStartScheduleUseCase(
                        year = state.startScheduleYearState,
                        month = (state.startScheduleMonthState),
                        day = state.startScheduleDayState,
                        hour = state.startScheduleHourOfDayState,
                        minute = state.startScheduleMinuteOfDay -1
                    )
                )
                viewModelScope.launch {
                    _addEditMedicationChannel.send(
                        AddEditMedicationUiEvents.OnStartDateSet
                    )
                }
            }
            /**Picker -> interval unit and value changed**/
            is AddEditMedicationEvents.OnMedicationIntervalUnitChanged -> {
                state.updateIntervalUnitState(event.intervalUnit)
                state.updateIntervalMaxValueState(
                    medicationUseCase.setIntervalValueUseCase(
                        intervalUnit = state.intervalUnitState
                    )
                )
            }
            is AddEditMedicationEvents.OnMedicationIntervalValueChanged -> {
                state.updateIntervalValueState(event.intervalValue)
            }

            is AddEditMedicationEvents.GetDatePart -> {
                when (event.part) {
                    DateParts.YEAR -> {
                        state.updateStartScheduleYearState(
                            medicationUseCase.getDateFromDbUseCase(
                                dateInMillis = state.medicationToEditState!!.startingSchedule,
                                requestedTime = DateParts.YEAR,
                                language = state.languageState!!
                            ).toInt()
                        )
                    }
                    DateParts.MONTH -> {
                        state.updateStartScheduleMonthState(
                            medicationUseCase.getDateFromDbUseCase(
                                dateInMillis = state.medicationToEditState!!.startingSchedule,
                                requestedTime = DateParts.MONTH,
                                language = state.languageState!!
                            ).toInt()
                        )
                    }
                    DateParts.DAY -> {
                        state.updateStartScheduleDayState(
                            medicationUseCase.getDateFromDbUseCase(
                                dateInMillis = state.medicationToEditState!!.startingSchedule,
                                requestedTime = DateParts.DAY,
                                language = state.languageState!!
                            ).toInt()
                        )
                    }
                    DateParts.HOUR -> {
                        state.updateStartScheduleHourOfDay(
                            medicationUseCase.getDateFromDbUseCase(
                                dateInMillis = state.medicationToEditState!!.startingSchedule,
                                requestedTime = DateParts.HOUR,
                                language = state.languageState!!
                            ).toInt()
                        )
                    }
                    DateParts.MINUTE -> {
                        state.updateStartScheduleMinuteOfDay(
                            medicationUseCase.getDateFromDbUseCase(
                                dateInMillis = state.medicationToEditState!!.startingSchedule,
                                requestedTime = DateParts.MINUTE,
                                language = state.languageState!!
                            ).toInt()
                        )
                    }
                }
            }
            is AddEditMedicationEvents.SetStartingScheduleToString -> {
                val result = medicationUseCase.dateToStringUseCase(
                    language = state.languageState!!,
                    timeInMillis = state.startingScheduleState,
                    months = state.monthsState
                )
                state.updateStartingScheduleToStringState(result)
            }

        }
    }

    private fun setMonths() {
        viewModelScope.launch {
            _addEditMedicationChannel.send(
                AddEditMedicationUiEvents.GetMonths
            )
            if (state.modeState == Mode.EDIT) {
                getMedication()
            }
        }
    }

    private suspend fun getMedication() {

        val medication = medicationUseCase.getMedicationByIdUseCase(medicationId)

        state.updateMedicationToEditState(medication)
        state.updateDoctorNameState(medication.doctorName)
        state.updateApproximateUsagePeriodState(medication.approximateUsagePeriod)
        state.updateMedicationDosageState(medication.medicationDosage)
        state.updateMedicationImageUriState(medication.medicationImageUri)
        state.updateIntervalUnitState(medication.intervalUnit)
        state.updateIntervalValueState(medication.intervalValue)
        state.updateMedicineNameState(medication.medicineName)
        state.updateMedicationNotesState(medication.medicationNotes)
        state.updateNextScheduleState(medication.nextSchedule)
        state.updateNextScheduleToString(
            medicationUseCase.dateToStringUseCase(
                language = state.languageState!!,
                timeInMillis = medication.nextSchedule,
                months = state.monthsState
            )
        )
        state.updateStartingScheduleState(medication.startingSchedule)
        state.updateStartingScheduleToStringState(
            medicationUseCase.dateToStringUseCase(
                language = state.languageState!!,
                timeInMillis = medication.startingSchedule,
                months = state.monthsState
            )
        )
        state.updateMedicationStatusState(medication.medicationStatus)
        state.updateMedicationTypeState(medication.medicationType)

        _addEditMedicationChannel.send(AddEditMedicationUiEvents.LoadMedicationInfo)
    }

    private fun calculateNextSchedule(currentSchedule: Long) {
         state.updateNextScheduleState(
            medicationUseCase.calculateNextScheduleUseCase(
                currentSchedule = currentSchedule,
                intervalUnit = state.intervalUnitState,
                intervalValue = state.intervalValueState,
                language = state.languageState!!
            )
        )
    }

    private suspend fun insertSchedules() {
        if (state.modeState == Mode.ADD) {
           withContext(Dispatchers.IO){
               medicationId = medicationUseCase.getAllMedicationsDESCUseCase().first().medicationId
               }
        }
//        medicationUseCase.getNumberOfInsertIterationsUseCase(
//            approximateDays = state.approximateUsagePeriodState.toInt(),
//            cycleUnit = state.intervalUnitState,
//            cycleVal = state.intervalValueState
//        )
        for (cycle in 0.. 2

        ) {
            val schedule = Schedule(
                scheduleForPerson = userName,
                scheduleForMedication = state.medicineNameState,
                approximateUsagePeriod = state.approximateUsagePeriodState,
                medicationId = medicationId,
                userId = userId,
                nextSchedule = state.nextScheduleState,
                hasTaken = false,
            )
            withContext(Dispatchers.IO){
                medicationUseCase.insertScheduleUseCase(schedule)
            }
            withContext(Dispatchers.Default){
                calculateNextSchedule(state.nextScheduleState)
            }
        }
    }

    private suspend fun isEntriesOk(): Boolean {
        if (state.medicineNameState.isEmpty()) {
            _addEditMedicationChannel.send(
                AddEditMedicationUiEvents.ShowError(
                    UiText.ResourceString(R.string.noMedicineNameError)
                )
            )
            state.updateIsDataOkState(false)
            return state.isDataOkState
        }


        if (state.medicationDosageState.isEmpty()) {
            _addEditMedicationChannel.send(
                AddEditMedicationUiEvents.ShowError(
                    UiText.ResourceString(R.string.dosage_is_not_set)
                )
            )
            state.updateIsDataOkState(false)
            return state.isDataOkState
        }

        if (state.approximateUsagePeriodState.isEmpty()) {
            _addEditMedicationChannel.send(
                AddEditMedicationUiEvents.ShowError(
                    UiText.ResourceString(R.string.duration_is_not_set)
                )
            )
            state.updateIsDataOkState(false)
            return state.isDataOkState
        }
        if (state.doctorNameState.isEmpty()) {
            _addEditMedicationChannel.send(
                AddEditMedicationUiEvents.ShowError(
                    UiText.ResourceString(R.string.doctor_name_was_not_set)
                )
            )
            state.updateIsDataOkState(false)
            return state.isDataOkState
        }
        state.updateIsDataOkState(true)

        return state.isDataOkState
    }

    private suspend fun checkForDuplicates(): Boolean {

        val medications = medicationUseCase.getAllMedicationUseCase(userId = userId)

        viewModelScope.launch {
            medications
                .collect {
                    if (it.isNotEmpty()) {
                        val collect = it.stream()
                            .filter { p -> p.medicineName == state.medicineNameState }
                            .collect(Collectors.toList())
                        if (collect.isNotEmpty()) {
                            state.updateIsDataOkState(false)
                        } else {
                            state.updateIsDataOkState(true)
                        }
                    } else {
                        state.updateIsDataOkState(true)
                    }
                }
        }

        return state.isDataOkState
    }

    private suspend fun insertNewMed() {
        if (state.isDataOkState) {
            withContext(Dispatchers.IO) {
                medicationUseCase.insertMedicationUseCase(
                    medication = Medication(
                        userName = userName,
                        userId = userId,
                        laboratoryId = state.laboratoryIdState,
                        medicationId = state.medicationIdState,
                        medicineName = state.medicineNameState,
                        doctorName = state.doctorNameState,
                        medicationImageUri = state.medicationImageUriState,
                        medicationNotes = state.medicationNotesState,
                        medicationDosage = state.medicationDosageState,
                        startingSchedule = state.startingScheduleState,
                        nextSchedule = state.nextScheduleState,
                        intervalUnit = state.intervalUnitState,
                        intervalValue = state.intervalValueState,
                        approximateUsagePeriod = state.approximateUsagePeriodState,
                        medicationType = state.medicationTypeState,
                        medicationStatus = state.medicationStatusState
                    )
                )
            }
            setAlarm()
            withContext(Dispatchers.IO){
                insertSchedules()
            }
            _addEditMedicationChannel.send(
                AddEditMedicationUiEvents.NavigateWithResult(
                    result = ADD_MEDICINE_RESULT_OK
                )
            )
        }
    }

    private fun updateMedication() {
        if (state.isDataOkState) {
            viewModelScope.launch {

                if (state.medicationToEditState?.startingSchedule != state.startingScheduleState
                    || state.medicationToEditState?.intervalUnit != state.intervalUnitState
                    || state.medicationToEditState?.intervalValue != state.intervalValueState
                ) {
                    val schedules = state.medicationToEditState?.medicationId?.let {
                        medicationUseCase.getScheduleByMedicationIdUseCase(
                            medicationId = it
                        )
                    }
                    schedules.let {
                        for (schedule in schedules!!) {
                            medicationUseCase.deleteScheduleUseCase(schedule)
                        }
                    }
                    withContext(Dispatchers.Default) {
                        calculateNextSchedule(state.startingScheduleState)
                    }
                    insertSchedules()
                }

                state.medicationToEditState?.copy(
                    userName = userName,
                    userId = userId,
                    laboratoryId = state.laboratoryIdState,
                    medicationId = state.medicationIdState,
                    medicineName = state.medicineNameState,
                    medicationType = state.medicationTypeState,
                    doctorName = state.doctorNameState,
                    medicationImageUri = state.medicationImageUriState,
                    medicationNotes = state.medicationNotesState,
                    medicationDosage = state.medicationDosageState,
                    approximateUsagePeriod = state.approximateUsagePeriodState,
                    startingSchedule = state.startingScheduleState,
                    nextSchedule = state.nextScheduleState,
                    intervalUnit = state.intervalUnitState,
                    intervalValue = state.intervalValueState,
                    medicationStatus = state.medicationStatusState
                )?.let {
                    medicationUseCase.insertMedicationUseCase(
                        medication = it
                    )
                }



                _addEditMedicationChannel.send(
                    AddEditMedicationUiEvents.NavigateWithResult(
                        result = EDIT_MEDICINE_RESULT_OK
                    )
                )
            }
        }
    }

    private fun setAlarm() {
      alarmScheduler.setUserID(userId)
      alarmScheduler.setMedicationId(medicationId)
        alarmScheduler.schedule(
            item = AlarmItem(
                time = Instant.ofEpochMilli(state.nextScheduleState).atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            )
        )
    }
}