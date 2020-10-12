package com.benrostudios.syncure.ui.home.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.benrostudios.syncure.R
import com.benrostudios.syncure.data.models.Password
import com.benrostudios.syncure.utils.Constants.PASSWORD
import com.benrostudios.syncure.utils.Constants.SUCCESS
import com.benrostudios.syncure.utils.hide
import com.benrostudios.syncure.utils.longToaster
import com.benrostudios.syncure.utils.show
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_password_detail.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class PasswordDetail : BottomSheetDialogFragment() {

    private lateinit var passwordObj: Password


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val viewModel: PasswordViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        passwordObj = arguments?.getSerializable(PASSWORD) as Password
        if (passwordObj != null) {
            password_detail_password.text = passwordObj.code
            password_detail_title.text = passwordObj.title
        }
        password_delete.setOnClickListener {
            password_detail_progress.show()
            deletePassword(passwordObj._id)
        }
    }

    private fun deletePassword(id: String) {
        lifecycleScope.launch {
            viewModel.removePassword(id).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    if (it.status == SUCCESS) {
                        lifecycleScope.launch {
                            viewModel.getPasswords()
                        }
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
        password_detail_progress.hide()
    }


}