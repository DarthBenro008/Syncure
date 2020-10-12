package com.benrostudios.syncure.ui.auth

import android.os.Bundle
import android.widget.Toast
import com.benrostudios.syncure.R
import com.benrostudios.syncure.data.network.response.NetworkEventFailure
import com.benrostudios.syncure.ui.base.BaseActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class Auth : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkFailure(event: NetworkEventFailure) {
        Toast.makeText(this, "Something went wrong please try again later!", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}