package com.benrostudios.syncure.ui.auth.signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.benrostudios.syncure.R
import com.benrostudios.syncure.ui.auth.AuthViewModel
import com.benrostudios.syncure.ui.base.ScopedFragment
import com.benrostudios.syncure.utils.Constants.SUCCESS
import com.benrostudios.syncure.utils.shortToaster
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SignIn : ScopedFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val viewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signin_continue.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() = launch {
        viewModel.login("jane_doe", "123456").observe(viewLifecycleOwner, Observer {
            if (it.status == SUCCESS) {
                requireContext().shortToaster("Success")
            } else {
                requireActivity().shortToaster("Failure")
            }
        })
    }

}