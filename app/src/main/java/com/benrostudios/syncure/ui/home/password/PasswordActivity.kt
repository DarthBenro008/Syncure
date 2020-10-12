package com.benrostudios.syncure.ui.home.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.benrostudios.syncure.R
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        add_password.setOnClickListener {
            val addPasswordBottomSheet = AddPassword()
            addPasswordBottomSheet.show(supportFragmentManager, addPasswordBottomSheet.tag)
        }
    }
}