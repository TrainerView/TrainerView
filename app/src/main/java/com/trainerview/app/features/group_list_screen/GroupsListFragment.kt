package com.trainerview.app.features.group_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.trainerview.app.R
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.ui.BaseFragment
import com.trainerview.app.databinding.FragmentGroupListBinding
import com.trainerview.app.features.group_list_screen.di.DaggerGroupsListComponent
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
        initUi()
        viewModel.load()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateToolbar(showButtons = uiState.selectedGroupId != null)
                }
            }
        }
    }

    private fun initUi() {
        with(binding) {
            groupsRv.adapter = viewModel.adapter
            toolbar.apply {
                applySystemInsetsTop()
                setNavigationOnClickListener { viewModel.clearGroupSelection() }
            }
            createGroupBtn.setOnClickListener { viewModel.onCreateGroupClick() }
            deleteGroupButton.setOnClickListener { viewModel.onDeleteSelectedGroupClick() }
            editGroupButton.setOnClickListener { viewModel.onEditSelectedGroupClick() }
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
