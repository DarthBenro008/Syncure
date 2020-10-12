package com.benrostudios.syncure.ui.home.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.syncure.R
import com.benrostudios.syncure.adapters.withSimpleAdapter
import com.benrostudios.syncure.data.network.response.NetworkResult
import com.benrostudios.syncure.utils.Constants.PASSWORD
import com.benrostudios.syncure.utils.Constants.SUCCESS
import com.benrostudios.syncure.utils.errorSnackBar
import com.benrostudios.syncure.utils.hide
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.password_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class PasswordActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val viewModel: PasswordViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        job = Job()

        add_password.setOnClickListener {
            val addPasswordBottomSheet = AddPassword()
            addPasswordBottomSheet.show(supportFragmentManager, addPasswordBottomSheet.tag)
        }
        password_recycler.layoutManager = LinearLayoutManager(this)
        getPasswords()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getPasswords() = launch {
        viewModel.getPasswords()
        viewModel.passwordResponse.observeForever {
            if (it != null) {
                if (it.status == SUCCESS) {
                    stopLoading()
                    password_recycler.withSimpleAdapter(
                        it.data.foundItems!!,
                        R.layout.password_item
                    ) { data ->
                        itemView.password_item_title.text = data.title
                        itemView.setOnClickListener {
                            val bundle = Bundle()
                            bundle.putSerializable(PASSWORD, data)
                            val passwordDetail = PasswordDetail()
                            passwordDetail.arguments = bundle
                            passwordDetail.show(supportFragmentManager, passwordDetail.tag)
                        }
                    }
                } else {
                    password_recycler.errorSnackBar(it.message)
                    stopLoading()
                }

            } else {
                stopLoading()
            }
        }
    }

    private fun stopLoading() {
        password_progress.hide()
    }

    override fun onResume() {
        super.onResume()
        getPasswords()
    }
}