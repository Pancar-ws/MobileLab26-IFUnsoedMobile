package com.example.maestro.data.remote

import com.example.maestro.data.model.GempaResponse
import retrofit2.http.GET

interface GempaApiService {
    @GET("DataMKG/TEWS/gempaterkini.json")
    suspend fun getGempaTerkini(): GempaResponse
}
