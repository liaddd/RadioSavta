package com.liad.radiosavta.server_connection

import co.climacell.statefulLiveData.core.StatefulLiveData
import com.liad.radiosavta.models.Program
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiRequest {

    @GET("/programs")
    fun getPrograms(): StatefulLiveData<List<Program>>

    @GET("/programs/{programId}")
    fun getProgramById(@Path("programId") id: Int): StatefulLiveData<Program>
}