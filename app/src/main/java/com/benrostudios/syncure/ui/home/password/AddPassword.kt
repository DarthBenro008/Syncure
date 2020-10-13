package com.benrostudios.syncure.ui.home.password

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.benrostudios.syncure.R
import com.benrostudios.syncure.utils.Constants.SUCCESS
import com.benrostudios.syncure.utils.hide
import com.benrostudios.syncure.utils.longToaster
import com.benrostudios.syncure.utils.show
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_password.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class AddPassword : BottomSheetDialogFragment() {

    private val viewModel: PasswordViewModel by inject()
    private lateinit var passwordActivity: PasswordActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        passwordActivity = activity as PasswordActivity
        super.onActivityCreated(savedInstanceState)
        add_password_to_sync.setOnClickListener {
            validate()
            add_password_progress.show()
        }
    }

    private fun validate() {
        val passwordTitle = password_title_input.text.toString()
        val password = password_input.text.toString()
        if (passwordTitle.length >= 4 && password.length >= 2) {
            addPassword(passwordTitle, password)
            return
        }
        password_title_input.error = "Please enter valid title"
        password_input.error = "Please enter valid password"
    }

    private fun addPassword(passwordTitle: String, password: String) {
        lifecycleScope.launch {
            viewModel.addPassword(passwordTitle, password).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    if (it.status == SUCCESS) {
                        passwordActivity.toggleGetPasswords()
                        dismiss()
                    } else {
                        requireActivity().longToaster(it.message)
                        stopLoading()
                    }
                } else {
                    stopLoading()
                }
            })
        }
    }

    private fun stopLoading() {
        add_password_progress.hide()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        lifecycleScope.launch {
            viewModel.getPasswords()
        }
    }

}