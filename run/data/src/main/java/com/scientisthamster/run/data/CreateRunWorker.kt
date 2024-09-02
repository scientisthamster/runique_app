package com.scientisthamster.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.scientisthamster.core.database.dao.RunPendingSyncDao
import com.scientisthamster.core.database.mapper.toRun
import com.scientisthamster.core.domain.run.RemoteRunDataSource

class CreateRunWorker(
    context: Context,
    private val params: WorkerParameters,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val pendingSyncDao: RunPendingSyncDao
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        val runId = params.inputData.getString(RUN_ID) ?: return Result.failure()
        val runEntity = pendingSyncDao.getRunPendingSyncEntity(runId) ?: return Result.failure()
        val run = runEntity.runEntity.toRun()
        return when (val result = remoteRunDataSource.postRun(
            run = run,
            mapPicture = runEntity.mapPictureByteArray
        )) {
            is com.scientisthamster.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.scientisthamster.core.domain.util.Result.Success -> {
                pendingSyncDao.deleteRunPendingSyncEntity(runId)
                Result.success()
            }
        }
    }

    companion object {
        const val RUN_ID = "RUN_ID"
    }
}