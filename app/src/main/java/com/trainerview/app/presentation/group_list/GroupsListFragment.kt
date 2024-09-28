package com.trainerview.app.presentation.group_list

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.trainerview.app.R
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.BaseFragment
import com.trainerview.app.databinding.FragmentGroupListBinding
import com.trainerview.app.presentation.group_list.di.DaggerGroupsListComponent
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
        binding.fragmentGroupsListNewGroupBtn.setOnClickListener {
            viewModel.clearGroupSelection()
            findNavController().navigate(R.id.action_to_addGroupFragment)
        }
        binding.fragmentMainToolbar.applySystemInsetsTop()
        binding.fragmentGroupsGroupsRv.adapter = viewModel.adapter

        binding.fragmentMainToolbar.setNavigationOnClickListener {
            viewModel.clearGroupSelection()
        }

        binding.fragmentMainDeleteGroupButton.setOnClickListener {
            viewModel.deleteSelectedGroup()
        }

        binding.fragmentMainEditGroupButton.setOnClickListener {
            viewModel.clearGroupSelection()
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
                binding.fragmentMainToolbar.setNavigationIconTint(ContextCompat.getColor(binding.root.context, R.color.black))
                binding.fragmentMainDeleteGroupButton.isVisible = true
                binding.fragmentMainEditGroupButton.isVisible = true
            }
            false -> {
                binding.fragmentMainToolbar.setNavigationIconTint(ContextCompat.getColor(binding.root.context, R.color.white))
                binding.fragmentMainDeleteGroupButton.isVisible = false
                binding.fragmentMainEditGroupButton.isVisible = false
            }
        }
    }
}