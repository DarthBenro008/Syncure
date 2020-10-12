package com.benrostudios.syncure.ui.auth.signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.benrostudios.syncure.R
import com.benrostudios.syncure.ui.auth.AuthViewModel
import com.benrostudios.syncure.ui.base.ScopedFragment
import com.benrostudios.syncure.utils.*
import com.benrostudios.syncure.utils.Constants.SUCCESS
import com.benrostudios.syncure.utils.Constants.USERNAME
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SignIn : ScopedFragment() {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

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
            signin_progress.show()
            signin_continue.hide()
            validate()
        }
    }

    private fun validate() {
        val username = signin_username.text.toString()
        val password = signin_password.text.toString()
        if (signin_username.isValidAlphaNumeric("username") && signin_password.isValidAlphaNumeric("password")) {
            loginUser(username, password)
        }else{
            stopLoading()
        }
    }

    private fun loginUser(username: String, password: String) = launch {
        viewModel.login(username, password).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.status == SUCCESS) {
                    requireContext().shortToaster("Success")
                    val bundle = Bundle()
                    bundle.putString(USERNAME,username.trim())
                    if (navController.currentDestination?.id == R.id.signIn) {
                        navController.navigate(R.id.action_signIn_to_signinotp, bundle)
                    }
                } else {
                    signin_continue.errorSnackBar(it.message)
                    stopLoading()
                }
            } else {
                stopLoading()
            }
        })
    }

    private fun stopLoading() {
        signin_continue.show()
        signin_progress.gone()
    }

}