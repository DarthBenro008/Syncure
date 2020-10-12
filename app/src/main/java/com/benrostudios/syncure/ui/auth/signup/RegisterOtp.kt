package com.benrostudios.syncure.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.benrostudios.syncure.ui.home.Home
import com.benrostudios.syncure.R
import com.benrostudios.syncure.ui.auth.AuthViewModel
import com.benrostudios.syncure.ui.base.ScopedFragment
import com.benrostudios.syncure.utils.*
import com.benrostudios.syncure.utils.Constants.SUCCESS
import com.benrostudios.syncure.utils.Constants.USERNAME
import kotlinx.android.synthetic.main.fragment_register_otp.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class RegisterOtp : ScopedFragment() {

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_otp, container, false)
    }

    private val viewModel by viewModel<AuthViewModel>()
    private val sharedPrefManager: SharedPrefManager by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        username = arguments?.getString(USERNAME, "")
        signup_verify.setOnClickListener {
            signup_verify.hide()
            signup_verify_progress.show()
            validate("")
        }
        otp_view.setOtpCompletionListener {
            validate(it)
        }
    }

    private fun validate(otp: String) {
        val innerOtp = if (otp.isNotEmpty()) {
            otp_view.text.toString()
        } else {
            otp
        }
        val password = register_otp_password.text.toString()
        if (register_otp_password.isValidAlphaNumeric("password") && innerOtp.length == 6) {
            verifyUser(innerOtp, password)
        }
    }


    private fun verifyUser(totp: String, password: String) = launch {
        viewModel.registerTwoFactor(totp = totp, password = password, username = username!!)
            .observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    if (it.status == SUCCESS) {
                        sharedPrefManager.jwtStored = it.data.token.toString()
                        startActivity(Intent(requireActivity(), Home::class.java))
                        requireActivity().finish()
                    } else {
                        stopLoading()
                        signup_verify.errorSnackBar(it.message)
                    }
                } else {
                    stopLoading()
                }
            })
    }

    private fun stopLoading() {
        signup_verify.show()
        signup_verify_progress.hide()
    }


}