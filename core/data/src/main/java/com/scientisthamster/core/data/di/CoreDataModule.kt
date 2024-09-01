package com.scientisthamster.core.data.di

import com.scientisthamster.core.data.auth.EncryptedSessionStorage
import com.scientisthamster.core.data.networking.HttpClientFactory
import com.scientisthamster.core.data.run.OfflineFirstRunRepository
import com.scientisthamster.core.domain.SessionStorage
import com.scientisthamster.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single { HttpClientFactory(get()).build() }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}