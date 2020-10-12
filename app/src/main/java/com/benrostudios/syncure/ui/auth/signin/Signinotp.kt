package com.benrostudios.syncure.ui.auth.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.benrostudios.syncure.MainActivity
import com.benrostudios.syncure.R
import com.benrostudios.syncure.ui.auth.AuthViewModel
import com.benrostudios.syncure.ui.base.ScopedFragment
import com.benrostudios.syncure.utils.Constants.SUCCESS
import com.benrostudios.syncure.utils.Constants.USERNAME
import com.benrostudios.syncure.utils.SharedPrefManager
import com.benrostudios.syncure.utils.errorSnackBar
import com.benrostudios.syncure.utils.hide
import com.benrostudios.syncure.utils.show
import kotlinx.android.synthetic.main.fragment_signinotp.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class Signinotp : ScopedFragment() {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val viewModel by viewModel<AuthViewModel>()
    private val sharedPrefManager: SharedPrefManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signinotp, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val username = arguments?.getString(USERNAME, "")
        signin_otp.setOtpCompletionListener {
            signin_otp_progress.show()
            signin_otp_verify.hide()
            verifyOtp(it, username!!)
        }
        signin_otp_verify.setOnClickListener {
            signin_otp_progress.show()
            signin_otp_verify.hide()
            val otp = signin_otp.text.toString()
            if (otp.length == 6) {
                verifyOtp(otp, username!!)
            } else {
                stopLoading()
                signin_otp_verify.errorSnackBar("Enter proper OTP")
            }

        }
    }

    private fun verifyOtp(totp: String, username: String) = launch {
        viewModel.loginTwoFactor(totp, username).observe(viewLifecycleOwner, Observer {
            if (it.status == SUCCESS) {
                sharedPrefManager.jwtStored = it.data.token.toString()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            } else {
                stopLoading()
                signin_otp_verify.errorSnackBar(it.message)
            }
        })
    }

    private fun stopLoading() {
        signin_otp_verify.show()
        signin_otp_progress.hide()
    }
}