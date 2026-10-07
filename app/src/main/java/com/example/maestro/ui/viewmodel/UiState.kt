package com.example.maestro.ui.viewmodel

import com.example.maestro.data.model.Gempa

/**
 * Sealed interface UiState sesuai pola praktikum (Pola P5)
 * Mendefinisikan 3 kondisi utama: Loading, Success, dan Error.
 */
sealed interface UiState {
    object Loading : UiState
    data class Success(val gempaList: List<Gempa>) : UiState
    data class Error(val message: String) : UiState
}
