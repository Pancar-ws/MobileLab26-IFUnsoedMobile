package com.example.maestro.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maestro.data.model.Gempa
import com.example.maestro.data.repository.GempaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GempaViewModel(
    private val repository: GempaRepository = GempaRepository()
) : ViewModel() {

    // Internal state untuk data mentah dari API
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // State untuk input query pencarian lokal
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Menyimpan daftar gempa asli dari response API untuk kebutuhan filter & detail lookup
    private var allGempaList: List<Gempa> = emptyList()

    /**
     * StateFlow yang menggabungkan UiState dengan query pencarian lokal (filter berdasarkan Wilayah)
     */
    val filteredGempaState: StateFlow<UiState> = combine(_uiState, _searchQuery) { state, query ->
        if (state is UiState.Success) {
            val filtered = if (query.isBlank()) {
                state.gempaList
            } else {
                state.gempaList.filter { gempa ->
                    gempa.wilayah.contains(query.trim(), ignoreCase = true)
                }
            }
            UiState.Success(filtered)
        } else {
            state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    init {
        fetchGempaData()
    }

    /**
     * Memanggil API BMKG dan mengupdate StateFlow
     */
    fun fetchGempaData() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val data = repository.getGempaTerkini()
                allGempaList = data
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.localizedMessage ?: "Terjadi kesalahan saat mengambil data dari server BMKG."
                )
            }
        }
    }

    /**
     * Mengupdate kata kunci pencarian
     */
    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    /**
     * Mengambil item gempa berdasarkan indeks dari daftar asli
     */
    fun getGempaByIndex(index: Int): Gempa? {
        return allGempaList.getOrNull(index)
    }

    /**
     * Mendapatkan indeks asli dari sebuah objek Gempa
     */
    fun getOriginalIndex(gempa: Gempa): Int {
        return allGempaList.indexOf(gempa).coerceAtLeast(0)
    }
}
