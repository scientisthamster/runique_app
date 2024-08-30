package com.scientisthamster.run.network

import com.scientisthamster.core.data.networking.constructRoute
import com.scientisthamster.core.data.networking.delete
import com.scientisthamster.core.data.networking.get
import com.scientisthamster.core.data.networking.safeCall
import com.scientisthamster.core.domain.run.RemoteRunDataSource
import com.scientisthamster.core.domain.run.Run
import com.scientisthamster.core.domain.util.DataError
import com.scientisthamster.core.domain.util.EmptyResult
import com.scientisthamster.core.domain.util.Result
import com.scientisthamster.core.domain.util.map
import com.scientisthamster.run.network.mapper.toCreateRunRequest
import com.scientisthamster.run.network.mapper.toRun
import com.scientisthamster.run.network.model.RunDto
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorRemoteRunDataSource(
    private val httpClient: HttpClient
) : RemoteRunDataSource {

    override suspend fun getRuns(): Result<List<Run>, DataError.Network> {
        return httpClient.get<List<RunDto>>(route = "/runs").map { it.map(RunDto::toRun) }
    }

    override suspend fun postRun(run: Run, mapPicture: ByteArray): Result<Run, DataError.Network> {
        val createRunRequestJson = Json.encodeToString(run.toCreateRunRequest())
        val result = safeCall<RunDto> {
            httpClient.submitFormWithBinaryData(
                url = constructRoute("/run"),
                formData = formData {
                    append("MAP_PICTURE", mapPicture, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=mappicture.jpg")
                    })
                    append("RUN_DATA", createRunRequestJson, Headers.build {
                        append(HttpHeaders.ContentType, "text/plain")
                        append(HttpHeaders.ContentDisposition, "form-data; name=\"RUN_DATA\"")
                    })
                }
            ) {
                method = HttpMethod.Post
            }
        }

        return result.map(RunDto::toRun)
    }

    override suspend fun deleteRun(id: String): EmptyResult<DataError.Network> {
        return httpClient.delete("/run", queryParameters = mapOf("id" to id))
    }
}