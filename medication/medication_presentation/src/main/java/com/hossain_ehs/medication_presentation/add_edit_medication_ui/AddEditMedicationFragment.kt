package com.hossain_ehs.medication_presentation.add_edit_medication_ui

import android.app.ActivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hossain_ehs.core.util.AddEditMedicationUiEvents
import com.hossain_ehs.core.util.Mode
import com.hossain_ehs.medication_domain.model.medication.DateParts
import com.hossain_ehs.medication_presentation.R
import com.hossain_ehs.medication_presentation.databinding.FragmentAddEditMedicationBinding
import com.shawnlin.numberpicker.NumberPicker
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext


@AndroidEntryPoint
class AddEditMedicationFragment : Fragment(R.layout.fragment_add_edit_medication),
    NumberPicker.OnValueChangeListener {

    private lateinit var binding: FragmentAddEditMedicationBinding
    private val viewModel: AddEditMedicationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddEditMedicationBinding.bind(view)
        subscribeToObservers()

        binding.apply {

            /**edit existing medication**/
            if (viewModel.state.modeState == Mode.EDIT) {
                imgMedicineImage.visibility = View.VISIBLE
                linearSelectInterval.visibility = View.GONE
                linearSelectStartSchedule.visibility = View.GONE
                btnDone.visibility = View.GONE
                lblSelectInterval.visibility = View.GONE
                fabSaveMedication.visibility = View.VISIBLE


//                addEditMedicationViewModel.medication?.let {
//                    addEditMedicationViewModel.getCorrespondingMedicationImages(
//                        it
//                    )
//                }
            }
            /** add new medication **/
            else {
                viewModel.onEvent(
                    AddEditMedicationEvents
                        .SetMonths(
                            resources.getStringArray(com.hossain_ehs.core.R.array.months)
                        )
                )
                setInitialDates()
                imgNewMedicineImage.visibility = View.VISIBLE
                lblSelectInterval.visibility = View.GONE
                textLayoutSelectedStartingDate.visibility = View.GONE
                editTextNextScheduleDate.visibility = View.GONE
                linearSelectInterval.visibility = View.GONE

            }

            txtPatientName.setText(viewModel.state.userNameState)
            txtPatientName.jumpDrawablesToCurrentState()

            editTextMedicineName.doOnTextChanged { text, _, _, _ ->
                if (text!!.isNotEmpty()) {
                    viewModel.onEvent(
                        AddEditMedicationEvents
                            .OnMedicationNameChanged(text.toString())
                    )
                }
            }

            editTextShortDescription.setText(viewModel.state.medicationNotesState)
            editTextShortDescription.doOnTextChanged { text, _, _, _ ->
                if (text!!.isNotEmpty()) {
                    viewModel.onEvent(
                        AddEditMedicationEvents
                            .OnMedicationNotesChanged(text.toString())


                    )
                }
            }

            editTextDosage.doOnTextChanged { text, _, _, _ ->
                if (text!!.isNotEmpty()) {
                    viewModel.onEvent(
                        AddEditMedicationEvents
                            .OnMedicationDosageChanged(text.toString())
                    )
                }
            }

            editTextDoctorName.doOnTextChanged { text, _, _, _ ->
                if (text!!.isNotEmpty()) {
                    viewModel.onEvent(
                        AddEditMedicationEvents
                            .OnDoctorNameChanged(text.toString())
                    )
                }
            }

            editTextUsagePeriod.doOnTextChanged { text, _, _, _ ->
                if (text!!.isNotEmpty()) {
                    viewModel.onEvent(
                        AddEditMedicationEvents
                            .OnMedicationApproximateUsagePeriodChanged(text.toString())
                    )
                }
            }
            /**button add edit start schedule **/
            textLayoutSelectedStartingDate.setEndIconOnClickListener {
                linearSelectStartSchedule.visibility = View.VISIBLE
                btnDone.visibility = View.VISIBLE
                lblSelectInterval.visibility = View.GONE
                linearSelectInterval.visibility = View.GONE
                textLayoutSelectedStartingDate.visibility = View.GONE
                fabSaveMedication.visibility = View.GONE
                editTextNextScheduleDate.visibility = View.GONE
            }

            /**set pickers listeners  **/
            pickerYear.setOnValueChangedListener(this@AddEditMedicationFragment)
            pickerMonth.setOnValueChangedListener(this@AddEditMedicationFragment)
            pickerDay.setOnValueChangedListener(this@AddEditMedicationFragment)
            pickerHour.setOnValueChangedListener(this@AddEditMedicationFragment)
            pickerMinutes.setOnValueChangedListener(this@AddEditMedicationFragment)
            pickerIntervalUnit.setOnValueChangedListener(this@AddEditMedicationFragment)
            pickerIntervalValue.setOnValueChangedListener(this@AddEditMedicationFragment)


            /**button done selecting schedule **/
            btnDone.setOnClickListener {
                viewModel.onEvent(AddEditMedicationEvents.OnButtonDoneClicked)
                fabSaveMedication.visibility = View.VISIBLE
            }
            /**button finish add/edit medication **/
            fabSaveMedication.setOnClickListener {
                viewModel.onEvent(AddEditMedicationEvents.AddMedication)
            }
        }
    }


    private fun subscribeToObservers() {
        viewModel.addEditMedicationChannel.asLiveData().observe(viewLifecycleOwner) { event ->
            when (event) {
                is AddEditMedicationUiEvents.LoadMedicationInfo -> {
                    setInitialDates()
                    binding.apply {
                        editTextMedicineName.setText(viewModel.state.medicineNameState)
                        editTextDosage.setText(viewModel.state.medicationDosageState)
                        editTextDoctorName.setText(viewModel.state.doctorNameState)
                        editTextUsagePeriod.setText(viewModel.state.approximateUsagePeriodState)
                        editTextStartingDate.setText(viewModel.state.startingScheduleToStringState)
                        editTextNextScheduleDate.setText(viewModel.state.nextScheduleToStringState)
                    }
                }
                is AddEditMedicationUiEvents.NavigateToAddMedicationPhoto -> {
                    /**
                     *  implement this later
                     * **/
                }
                is AddEditMedicationUiEvents.OnStartDateSet -> {
                    binding.apply {
                        textLayoutSelectedStartingDate.visibility = View.VISIBLE
                        lblSelectInterval.visibility = View.VISIBLE
                        linearSelectInterval.visibility = View.VISIBLE
                        viewModel.onEvent(
                            AddEditMedicationEvents.SetStartingScheduleToString
                        )
                        editTextStartingDate.setText(
                            viewModel.state.startingScheduleToStringState
                        )
                        linearSelectStartSchedule.visibility = View.GONE
                        viewModel.onEvent(
                            AddEditMedicationEvents.SetStartingScheduleToString
                        )
                    }
                }
                is AddEditMedicationUiEvents.NavigateWithResult -> {
                    setFragmentResult(
                        "add_edit_medication_status",
                        bundleOf(
                            "add_edit_medication_status"
                                    to event.result
                        )
                    )
                    findNavController().popBackStack()
                }
                is AddEditMedicationUiEvents.ShowError -> {
                    Snackbar.make(
                        requireView(),
                        event.message.asString(requireContext()),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is AddEditMedicationUiEvents.GetMonths -> {
                    viewModel.onEvent(
                        AddEditMedicationEvents.SetMonths(
                            requireContext()
                                .resources
                                .getStringArray(
                                    com.hossain_ehs.core.R.array.months
                                )
                        )
                    )
                }
            }
        }
    }

    private fun setInitialDates() {
        binding.apply {
            val years = viewModel.state.yearsStates
            pickerYear.displayedValues = years
            pickerYear.minValue = years[0].toInt()
            pickerYear.maxValue = years[years.size - 1].toInt()
            pickerYear.value = viewModel.state.startScheduleYearState

            /** set current year for yearPicker **/
            if (viewModel.state.modeState == Mode.ADD) {
                viewModel.onEvent(
                    AddEditMedicationEvents.SetInitialYearPickerValue
                )
            } else {
                viewModel.onEvent(
                    AddEditMedicationEvents.GetDatePart(
                        DateParts.YEAR
                    )
                )
            }
            pickerYear.value = viewModel.state.startScheduleYearState
            /** set month datePicker **/
            pickerMonth.displayedValues = viewModel.state.monthsState
            pickerMonth.minValue = 1
            pickerMonth.maxValue = 12

            if (viewModel.state.modeState == Mode.ADD) {
                viewModel.onEvent(
                    AddEditMedicationEvents.SetInitialMonthPickerValue
                )
            } else {
                viewModel.onEvent(
                    AddEditMedicationEvents.GetDatePart(
                        DateParts.MONTH
                    )
                )
            }
            pickerMonth.value = viewModel.state.startScheduleMonthState

            /** get number of day in month **/

            viewModel.onEvent(
                AddEditMedicationEvents.SetDaysInMonthPickerValue
            )

            /** set day datePicker **/
            val days = viewModel.state.daysInMonthMaxValState
            pickerDay.minValue = days[0].toInt()
            pickerDay.maxValue = viewModel.state.daysInMonthMaxValState.size
            if (viewModel.state.modeState == Mode.ADD) {
                viewModel.onEvent(
                    AddEditMedicationEvents.SetInitialDayInMonthPickerValue
                )
            } else {
                viewModel.onEvent(
                    AddEditMedicationEvents.GetDatePart(
                        DateParts.DAY
                    )
                )
            }
            pickerDay.value = viewModel.state.startScheduleDayState

            /** set hours Picker **/
            viewModel.onEvent(
                AddEditMedicationEvents.SetHours
            )
            val hours = viewModel.state.startScheduleHoursState
            pickerHour.displayedValues = hours
            pickerHour.minValue = 1
            pickerHour.maxValue = 24
            if (viewModel.state.modeState == Mode.ADD) {
                viewModel.onEvent(
                    AddEditMedicationEvents.SetInitialHourPickerValue
                )
            } else {
                viewModel.onEvent(
                    AddEditMedicationEvents.GetDatePart(
                        DateParts.HOUR
                    )
                )
            }
            pickerHour.value = viewModel.state.startScheduleHourOfDayState

            /** set minutes Picker **/
            viewModel.onEvent(
                AddEditMedicationEvents.SetMinutes
            )
            val minutes = viewModel.state.startScheduleMinutesState
            pickerMinutes.displayedValues = minutes
            pickerMinutes.minValue = 1
            pickerMinutes.maxValue = 60
            if (viewModel.state.modeState == Mode.ADD) {
                viewModel.onEvent(
                    AddEditMedicationEvents.SetInitialMinutePickerValue
                )
            } else {
                viewModel.onEvent(
                    AddEditMedicationEvents.GetDatePart(
                        DateParts.MINUTE
                    )
                )
            }
            pickerMinutes.value = viewModel.state.startScheduleMinuteOfDay
            /** set interval unit Picker **/
            val intervalsUnits = requireContext()
                .resources.getStringArray(com.hossain_ehs.core.R.array.units)
            pickerIntervalUnit.displayedValues = intervalsUnits
            pickerIntervalUnit.minValue = 0
            pickerIntervalUnit.value = viewModel.state.intervalUnitState
            pickerIntervalUnit.maxValue = 4


            /** set interval value Picker **/

            pickerIntervalValue.minValue = 1
            pickerIntervalValue.maxValue = viewModel.state.intervalMaxValueState
            pickerIntervalValue.value = viewModel.state.intervalValueState

        }
    }


    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        binding.apply {
            when (picker) {
                /** on year picker changed **/
                pickerYear -> {
                    if (newVal != oldVal) {
                        viewModel.onEvent(
                            AddEditMedicationEvents.SetYearPickerValue(
                                newVal
                            )
                        )
                    }
                    pickerDay.maxValue = viewModel.state.daysInMonthMaxValState.size
                }
                /** on month picker changed **/
                pickerMonth -> {
                    if (newVal != oldVal) {
                        viewModel.onEvent(
                            AddEditMedicationEvents.SetMonthPickerValue(
                                newVal
                            )
                        )
                    }
                    pickerDay.maxValue = viewModel.state.daysInMonthMaxValState.size
                }
                /** on day picker changed **/
                pickerDay -> {
                    if (newVal != oldVal) {
                        viewModel.onEvent(
                            AddEditMedicationEvents.SetDayPickerValue(
                                newVal
                            )
                        )
                    }
                    pickerDay.maxValue = viewModel.state.daysInMonthMaxValState.size
                }
                /** on hour picker changed **/
                pickerHour -> {
                    if (newVal != oldVal) {
                        viewModel.onEvent(
                            AddEditMedicationEvents.SetHourPickerValue(
                                newVal
                            )
                        )
                    }
                }
                /** on minute picker changed **/
                pickerMinutes -> {
                    if (newVal != oldVal) {
                        viewModel.onEvent(
                            AddEditMedicationEvents.SetMinutePickerValue(
                                newVal
                            )
                        )
                    }
                }
                /** on interval unit picker changed **/
                pickerIntervalUnit -> {
                    if (newVal != oldVal) {
                        viewModel.onEvent(
                            AddEditMedicationEvents.OnMedicationIntervalUnitChanged(
                                newVal
                            )
                        )
                        pickerIntervalValue.maxValue = viewModel.state.intervalMaxValueState
                    }

                }
                /** on interval unit value picker changed **/
                pickerIntervalValue -> {
                    if (newVal != oldVal) {
                        viewModel.onEvent(
                            AddEditMedicationEvents.OnMedicationIntervalValueChanged(
                                newVal
                            )
                        )
                    }
                }
            }
        }
    }

}

