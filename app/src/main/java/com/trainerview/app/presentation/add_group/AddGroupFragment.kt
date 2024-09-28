package com.trainerview.app.presentation.add_group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.trainerview.app.R
import com.trainerview.app.app.AppComponent
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.BaseFragment
import com.trainerview.app.databinding.FragmentAddGroupBinding
import com.trainerview.app.di.ScreenComponent
import com.trainerview.app.presentation.add_group.di.DaggerAddGroupComponent

class AddGroupFragment : BaseFragment<FragmentAddGroupBinding, AddGroupViewModel>() {

    override val viewModel by injectViewModel<AddGroupViewModel>()
    override fun diComponent() = DaggerAddGroupComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddGroupBinding {
        return FragmentAddGroupBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.fragmentAddGroupToolbar) {
            setNavigationOnClickListener { findNavController().popBackStack() }
            applySystemInsetsTop()
        }

        binding.fragmentAddGroupSaveButton.setOnClickListener {
            viewModel.createGroup(binding.fragmentAddGroupNameInput.text.toString())
            findNavController().popBackStack()
        }
    }
}