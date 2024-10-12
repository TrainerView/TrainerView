package com.trainerview.app.presentation.group_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.trainerview.app.R
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.BaseFragment
import com.trainerview.app.databinding.FragmentGroupListBinding
import com.trainerview.app.presentation.group_list.di.DaggerGroupsListComponent
import com.trainerview.app.presentation.update_group.UpdateGroupType
import kotlinx.coroutines.launch


class GroupsListFragment : BaseFragment<FragmentGroupListBinding, GroupsListViewModel>() {

    override val viewModel by injectViewModel<GroupsListViewModel>()
    override fun diComponent() = DaggerGroupsListComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGroupListBinding {
        return FragmentGroupListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newGroupBtn.setOnClickListener {
            viewModel.clearGroupSelection()
            findNavController().navigate(
                GroupsListFragmentDirections.actionToAddGroupFragment(
                    UpdateGroupType.CreateGroup
                )
            )
        }
        binding.toolbar.applySystemInsetsTop()
        binding.groupsRv.adapter = viewModel.adapter

        binding.toolbar.setNavigationOnClickListener {
            viewModel.clearGroupSelection()
        }


        binding.deleteGroupButton.setOnClickListener {
            viewModel.deleteSelectedGroup()
        }

        binding.editGroupButton.setOnClickListener {
            viewModel.uiState.value.groups.firstOrNull {
                it.id == viewModel.uiState.value.selectedGroupId
            }?.let { selectedGroup ->
                viewModel.clearGroupSelection()
                findNavController().navigate(
                    GroupsListFragmentDirections.actionToAddGroupFragment(
                        UpdateGroupType.EditGroup(
                            groupId = selectedGroup.id,
                            groupName = selectedGroup.name
                        )
                    )
                )
            }
        }

        viewModel.load()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateToolbar(showButtons = uiState.selectedGroupId != null)

                }
            }
        }
    }

    private fun updateToolbar(showButtons: Boolean) {
        when (showButtons) {
            true -> {
                binding.toolbar.setNavigationIconTint(ContextCompat.getColor(binding.root.context, R.color.black))
                binding.deleteGroupButton.isVisible = true
                binding.editGroupButton.isVisible = true
            }
            false -> {
                binding.toolbar.setNavigationIconTint(ContextCompat.getColor(binding.root.context, R.color.white))
                binding.deleteGroupButton.isVisible = false
                binding.editGroupButton.isVisible = false
            }
        }
    }
}