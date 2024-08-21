package com.scientisthamster.auth.data.di

import com.scientisthamster.auth.data.AuthRepositoryImpl
import com.scientisthamster.auth.data.EmailPatternValidator
import com.scientisthamster.auth.domain.AuthRepository
import com.scientisthamster.auth.domain.PatternValidator
import com.scientisthamster.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> { EmailPatternValidator }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}