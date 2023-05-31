package com.hossain_ehs.user_presentation.add_edit_user_ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hossain_ehs.core.util.AddEditUserUiEvents
import com.hossain_ehs.core.util.Mode
import com.hossain_ehs.tracker_presentation.R
import com.hossain_ehs.tracker_presentation.databinding.FragmentAddEditUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditUserFragment : Fragment(R.layout.fragment_add_edit_user) {

    private lateinit var binding: FragmentAddEditUserBinding
    private val viewModel: AddEditUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddEditUserBinding.bind(view)
        subscribeToObservers()

        binding.apply {


            editTxtName.addTextChangedListener { text ->
                viewModel.onEvent(AddEditUserEvents.OnUserFirstNameChanged(text.toString()))
            }

            editTxtLastName.addTextChangedListener { text ->
                viewModel.onEvent(AddEditUserEvents.OnUserLastNameChanged(text.toString()))
            }

            editTxtAge.addTextChangedListener { text ->
                viewModel.onEvent(AddEditUserEvents.OnUserAgeChanged(text.toString().toInt()))
            }

            editTxtTel.addTextChangedListener { text ->
                viewModel.onEvent(AddEditUserEvents.OnUserTelChanged(text.toString().toLong()))
            }

            editTxtAddress.addTextChangedListener { text ->
                viewModel.onEvent(AddEditUserEvents.OnUserAddressChanged(text.toString()))
            }

            editTxtEmergencyNum.addTextChangedListener { text ->
                viewModel.onEvent(
                    AddEditUserEvents.OnUserEmergencyNumberChanged(
                        text.toString().toLong()
                    )
                )
            }

            fabSaveUser.setOnClickListener {
                if (viewModel.state.mode == Mode.ADD){
                    viewModel.onEvent(AddEditUserEvents.OnSaveUserClicked)
                }
                else {
                    viewModel.onEvent(AddEditUserEvents.OnUpdateUserClicked)
                }
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.addEditUserChannel.asLiveData().observe(viewLifecycleOwner) { event ->
            when (event) {
                is AddEditUserUiEvents.NavigateToAddUserPhoto -> {

                }
                is AddEditUserUiEvents.ShowError -> {
                    Snackbar.make(
                        requireView(),
                        event.message.asString(requireContext()),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
                is AddEditUserUiEvents.NavigateWithResult -> {
                    setFragmentResult(
                        "add_edit_status", bundleOf(
                            "add_edit_status" to
                                    event.result
                        )
                    )
                    findNavController().popBackStack()
                }
                AddEditUserUiEvents.LoadUserInfo -> {
                    binding.apply {
                        editTxtName.setText(viewModel.state.userFirstName)
                        editTxtLastName.setText(viewModel.state.userLastName)
                        editTxtAge.setText(viewModel.state.userAge.toString())
                        editTxtTel.setText(viewModel.state.userTel.toString())
                        editTxtAddress.setText(viewModel.state.userAddress)
                        editTxtEmergencyNum.setText(viewModel.state.userEmergencyNumber.toString())
                    }
                }
            }
        }
    }
}


