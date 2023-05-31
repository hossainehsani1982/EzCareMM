package com.hossain_ehs.medication_presentation.medication_ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hossain_ehs.core.util.MedicationUiEvents
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_presentation.R
import com.hossain_ehs.medication_presentation.databinding.FragmentMedicationBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MedicationFragment : Fragment(R.layout.fragment_medication),
    MedicationAdapter.OnMedicationItemClickedListener {
    private lateinit var binding: FragmentMedicationBinding
    private val viewModel: MedicationViewModel by viewModels()
    private lateinit var  medicationAdapter : MedicationAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMedicationBinding.bind(view)
        medicationAdapter = MedicationAdapter(
            listener = this,
            language = Locale.getDefault().displayLanguage,
            months = resources.getStringArray(com.hossain_ehs.core.R.array.months),
            medicationUseCases = viewModel.medicationUseCase
        )
        subscribeToObservers()

        binding.apply {
            recyclerViewMedications.apply {
                addItemDecoration(
                    DividerItemDecoration(
                        view.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
                adapter = medicationAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            viewModel.state.medicationsState?.let {
                    it.observe(viewLifecycleOwner){medications->
                        medicationAdapter.submitList(medications)
                }
            }

            fabAddNewMedication.setOnClickListener {
                viewModel.onEvent(MedicationEvents.OnAddMedicationClicked)
            }
        }

        setFragmentResultListener("add_edit_medication_status"){ _, bundle ->
            val result = bundle.getInt("add_edit_medication_status")
            viewModel.onEvent(MedicationEvents.OnResultReceived(result))
        }
    }

    private fun subscribeToObservers(){
        viewModel.medicationChannel.asLiveData().observe(viewLifecycleOwner){event->
            when(event) {
                MedicationUiEvents.NavigateToAddNewMedication -> {
                    val action = MedicationFragmentDirections
                        .actionMedicationFragmentToAddEditMedicationFragment(
                            userName = viewModel.state.userNameState,
                            medId = 0,
                            userId = viewModel.userId!!,
                            userImageUri = viewModel.state.userImageUriState
                        )
                    findNavController().navigate(action)
                }
                is MedicationUiEvents.NavigateToEditMedication -> {
                    val action = MedicationFragmentDirections
                        .actionMedicationFragmentToAddEditMedicationFragment(
                            userName = viewModel.state.userNameState,
                            medId = event.id,
                            userId = viewModel.userId!!,
                            userImageUri = viewModel.state.userImageUriState
                        )
                    findNavController().navigate(action)
                }
                is MedicationUiEvents.ShowMessage -> {
                    Snackbar.make(requireView(),
                        event.message.asString(requireContext()),
                        Snackbar.LENGTH_LONG)
                        .show()
                }
                is MedicationUiEvents.ShowUndoDeleteMedicationMessage -> {
                    Snackbar.make(requireView(),
                        "Medication deleted",
                        Snackbar.LENGTH_LONG)
                        .setAction("Undo"){
                            viewModel.onEvent(MedicationEvents.OnUndoDeleteMedicationClicked(
                                viewModel.state.deletedMedicationState!!
                            ))
                        }
                        .show()
                }
            }
        }
    }

    override fun onEditMedicationClicked(medication: Medication) {
        viewModel.onEvent(MedicationEvents.OnEditMedicationClicked(medication))
    }

    override fun onDeleteMedicationClicked(medication: Medication) {
        viewModel.onEvent(MedicationEvents.OnDeleteMedicationClicked(medication))
    }
}

