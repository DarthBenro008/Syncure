package com.benrostudios.syncure.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.benrostudios.syncure.R
import com.benrostudios.syncure.ui.auth.AuthViewModel
import com.benrostudios.syncure.ui.base.ScopedFragment
import com.benrostudios.syncure.utils.*
import com.benrostudios.syncure.utils.Constants.USERNAME
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class SignUp : ScopedFragment() {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    private val viewModel by viewModel<AuthViewModel>()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signup_continue.setOnClickListener {
            signup_continue.hide()
            signup_progress.show()
            validate()
        }
    }

    private fun validate() {
        val email = signup_email.text.toString()
        val username = signup_username.text.toString()
        val name = signup_name.text.toString()
        if (signup_email.isValidEmail() && signup_username.isValidAlphaNumeric("username")) {
            registerUser(email, username, name)
        }else{
            stopLoading()
        }
    }

    private fun registerUser(email: String, username: String, name: String) = launch {
        viewModel.register(username, name, email).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.status == Constants.SUCCESS) {
                    val bundle = Bundle()
                    bundle.putString(USERNAME, username)
                    navController.navigate(R.id.action_signUp_to_registerOtp, bundle)
                } else {
                    signup_continue.errorSnackBar(it.message)
                    stopLoading()
                }
            } else {
                stopLoading()
            }
        })
    }

    private fun stopLoading() {
        signup_continue.show()
        signup_progress.hide()
    }


}