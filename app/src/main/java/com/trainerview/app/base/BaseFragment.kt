package com.trainerview.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.trainerview.app.di.ScreenComponent
import kotlinx.coroutines.launch
import java.util.logging.Logger

abstract class BaseFragment<T : ViewBinding, VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM
    private var _binding: T? = null
    protected val binding get() = _binding!!

    open val viewModelFactory: ViewModelProvider.Factory by lazy {
        with(diComponent()) {
            argumentsHandler.arguments = arguments
            return@with viewModelFactory
        }
    }

    protected abstract fun diComponent(): ScreenComponent

    inline fun <reified VM : BaseViewModel> injectViewModel() = viewModels<VM>(
        factoryProducer = { viewModelFactory }
    )

    abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navEvents.collect { navEvent ->
                    navEvent?.navigate(this@BaseFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun View.applySystemInsetsTop() {
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, v.paddingBottom)
            insets
        }
    }
}