package com.benrostudios.syncure.di.modules

import com.benrostudios.syncure.ui.auth.AuthViewModel
import com.benrostudios.syncure.ui.home.password.PasswordViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { PasswordViewModel(get()) }
}