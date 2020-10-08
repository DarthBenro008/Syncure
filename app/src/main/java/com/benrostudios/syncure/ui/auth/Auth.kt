package com.benrostudios.syncure.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.benrostudios.syncure.R
import com.benrostudios.syncure.ui.base.BaseActivity

class Auth : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}