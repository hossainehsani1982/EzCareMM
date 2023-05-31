package com.hossain_ehs.user_presentation.users_ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hossain_ehs.core.util.UserUiEvents
import com.hossain_ehs.tracker_presentation.R
import com.hossain_ehs.tracker_presentation.databinding.FragmentUsersBinding
import com.hossain_ehs.user_domain.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users),
    UsersAdapter.OnUserItemClickedListener {
    private lateinit var binding: FragmentUsersBinding
    private val viewModel: UserViewModel by viewModels()
    private val userAdapter = UsersAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersBinding.bind(view)
        subscribeToObservers()

        binding.apply {
            buttonAddUser.setOnClickListener {
                viewModel.onEvent(UserEvents.OnAddUserClicked)
            }

            recyclerViewUsers.apply {
                addItemDecoration(
                    DividerItemDecoration(
                        view.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
                adapter = userAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
           viewModel.state.users?.let {
               it.asLiveData().observe(viewLifecycleOwner){userList ->
                   userAdapter.submitList(userList)
               }
           }
        }

        setFragmentResultListener("add_edit_status"){ _, bundle ->
            val result = bundle.getInt("add_edit_status")
            viewModel.onEvent(UserEvents.OnResultReceived(result))
        }
    }

    private fun subscribeToObservers(){
        viewModel.userChannel.asLiveData().observe(viewLifecycleOwner){
                event ->
            when(event){
                is UserUiEvents.ShowMessage -> {
                  Snackbar.make(requireView(),
                      event.message.asString(requireContext()),
                      Snackbar.LENGTH_LONG).show()
                }
                is UserUiEvents.NavigateToAddNewUser -> {
                    val action = UsersFragmentDirections.actionUsersFragmentToAddEditUserFragment(
                        userId = 0
                    )
                    findNavController().navigate(action)
                }
                is UserUiEvents.NavigateToEditUser -> {
                    val action = UsersFragmentDirections.actionUsersFragmentToAddEditUserFragment(
                        userId = event.id
                    )
                    findNavController().navigate(action)
                }
                UserUiEvents.ShowUndoDeleteUserMessage -> {
                    Snackbar.make(requireView(),
                        "User deleted",
                        Snackbar.LENGTH_LONG).setAction("Undo"){
                        viewModel.onEvent(UserEvents
                            .OnUndoDeleteUserClicked(viewModel.state.deletedUser!!))
                    }.show()
                }
                is UserUiEvents.NavigateToMedication -> {
                     val deeplink = NavDeepLinkRequest.Builder.fromUri(
                            Uri.parse(
                                getString(com.hossain_ehs.core.R.string.deep_link_uri
                                    ).replace(
                                    "{userId}",
                                    event.id.toString()
                                )
                            )
                        ).build()
                    findNavController().navigate(deeplink)//
                }
            }
        }
    }

    override fun onImgGoToMedicationClicked(user: User) {
        viewModel.onEvent(UserEvents.OnToMedicationClicked(user))
    }

    override fun onEditUserClicked(user: User) {
       viewModel.onEvent(UserEvents.OnEditUserClicked(user))
    }

    override fun onDeleteUserClicked(user: User) {
       viewModel.onEvent(UserEvents.OnDeleteUserClicked(user))
    }
}

