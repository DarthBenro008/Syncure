package com.benrostudios.syncure.ui.home.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.syncure.R
import com.benrostudios.syncure.adapters.withSimpleAdapter
import com.benrostudios.syncure.data.network.response.NetworkResult
import com.benrostudios.syncure.utils.Constants.PASSWORD
import com.benrostudios.syncure.utils.Constants.SUCCESS
import com.benrostudios.syncure.utils.errorSnackBar
import com.benrostudios.syncure.utils.gone
import com.benrostudios.syncure.utils.hide
import com.benrostudios.syncure.utils.show
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.password_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class PasswordActivity : AppCompatActivity() {


    private val viewModel: PasswordViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        add_password.setOnClickListener {
            val addPasswordBottomSheet = AddPassword()
            addPasswordBottomSheet.show(supportFragmentManager, addPasswordBottomSheet.tag)
        }
        password_recycler.layoutManager = LinearLayoutManager(this)
        getPasswords()
        bottomSheetObserver()
    }


    private fun getPasswords() = lifecycleScope.launch {
        viewModel.getPasswords().observeForever {
            if (it != null) {
                if (it.status == SUCCESS) {
                    stopLoading()
                    if (it.data.foundItems.isNullOrEmpty()) {
                        no_password_placeholder.show()
                    } else {
                        no_password_placeholder.gone()
                        password_recycler.withSimpleAdapter(
                            it.data.foundItems,
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

    private fun bottomSheetObserver() = lifecycleScope.launch {
        viewModel.toggleBottomSheet.observeForever {
            if (it) {
                Log.d("gg", "activity $it")
                lifecycleScope.launch {
                    viewModel.getPasswords()
                }
                viewModel.toggleBottomSheet(false)
            } else {
                Log.d("gg", "activity $it")
            }
        }
    }

    fun toggleGetPasswords() = lifecycleScope.launch {
        password_progress.show()
        no_password_placeholder.hide()
        viewModel.getPasswords().observeForever {
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
}