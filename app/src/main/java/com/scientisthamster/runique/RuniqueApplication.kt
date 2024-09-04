package com.scientisthamster.runique

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import com.scientisthamster.auth.data.di.authDataModule
import com.scientisthamster.auth.presentation.di.authViewModelModule
import com.scientisthamster.core.data.di.coreDataModule
import com.scientisthamster.core.database.di.databaseModule
import com.scientisthamster.run.data.di.runDataModule
import com.scientisthamster.run.location.di.locationModule
import com.scientisthamster.run.network.di.networkModule
import com.scientisthamster.run.presentation.di.runPresentationModule
import com.scientisthamster.runique.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class RuniqueApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RuniqueApplication)
            workManagerFactory()
            modules(
                appModule,
                authDataModule,
                authViewModelModule,
                databaseModule,
                coreDataModule,
                locationModule,
                networkModule,
                runDataModule,
                runPresentationModule
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}