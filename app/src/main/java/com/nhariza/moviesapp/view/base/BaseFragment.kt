package com.nhariza.moviesapp.view.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialContainerTransform
import com.nhariza.moviesapp.R

abstract class BaseFragment<Binding : ViewBinding, ViewModel : BaseViewModel> : Fragment() {

    protected lateinit var binding: Binding
    protected abstract val viewModel: ViewModel

    protected abstract fun getViewBinding(): Binding
    protected abstract fun initView()
    protected abstract fun bindViewActions()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 300
            scrimColor = Color.TRANSPARENT
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewActions()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }

    protected open fun onBackPressed() {
        if (!findNavController().navigateUp()) {
            activity?.finish()
        }
    }

    protected fun showAlertDialog(
        title: String,
        message: String? = null,
        actionName: String,
        action: () -> Unit,
        actionCancel: (() -> Unit)? = null
    ) {
        with(context?.let { MaterialAlertDialogBuilder(it, R.style.AlertDialogTheme) }) {
            this?.setTitle(title)
            this?.setMessage(message)
            this?.setPositiveButton(actionName) { dialog, _ ->
                dialog.dismiss()
                action.invoke()
            }
            this?.setNegativeButton(getString(R.string.common_cancel)) { dialog, _ ->
                dialog.dismiss()
                actionCancel?.invoke()
            }
            this?.create()
            this?.setOnCancelListener { actionCancel?.invoke() }
            this?.show()
        }
    }
}