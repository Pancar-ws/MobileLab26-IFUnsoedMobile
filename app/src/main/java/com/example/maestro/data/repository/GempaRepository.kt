package com.example.maestro.data.repository

import com.example.maestro.data.model.Gempa
import com.example.maestro.data.remote.ApiClient
import com.example.maestro.data.remote.GempaApiService

class GempaRepository(
    private val apiService: GempaApiService = ApiClient.apiService
) {
    /**
     * Mengambil daftar gempa terkini dari REST API BMKG.
     * Mengembalikan List<Gempa>.
     */
    suspend fun getGempaTerkini(): List<Gempa> {
        val response = apiService.getGempaTerkini()
        return response.infoGempa?.gempaList ?: emptyList()
    }
}
