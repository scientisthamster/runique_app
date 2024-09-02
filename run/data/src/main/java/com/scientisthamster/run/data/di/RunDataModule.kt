package com.scientisthamster.run.data.di

import com.scientisthamster.core.domain.run.SyncRunScheduler
import com.scientisthamster.run.data.CreateRunWorker
import com.scientisthamster.run.data.DeleteRunWorker
import com.scientisthamster.run.data.FetchRunsWorker
import com.scientisthamster.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}