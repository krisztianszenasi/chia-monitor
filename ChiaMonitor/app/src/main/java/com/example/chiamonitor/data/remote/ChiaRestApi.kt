package com.example.chiamonitor.data.remote

import com.example.chiamonitor.data.remote.bakcup.BackupResponse
import com.example.chiamonitor.data.remote.services.ServicesReportDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST


interface ChiaRestApi {

    @GET("backup/current")
    suspend fun getBackup(): Response<BackupResponse>

    @DELETE("backup/current")
    suspend fun deleteBackup()

    @POST("backup")
    suspend fun createBackup()

    @GET("service-report")
    suspend fun getServiceReport(): Response<ServicesReportDto>

    companion object {
        const val BASE_URL = "http://10.0.2.2:5000"
    }
}