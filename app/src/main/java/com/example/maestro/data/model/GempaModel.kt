package com.example.maestro.data.model

import com.google.gson.annotations.SerializedName

/**
 * Root response dari REST API BMKG
 * Endpoint: https://data.bmkg.go.id/DataMKG/TEWS/gempaterkini.json
 */
data class GempaResponse(
    @SerializedName("Infogempa")
    val infoGempa: InfoGempa? = null
)

data class InfoGempa(
    @SerializedName("gempa")
    val gempaList: List<Gempa>? = null
)

/**
 * Model Data Item Gempa BMKG
 * Atribut minimal sesuai spesifikasi soal:
 * Tanggal, Jam, Coordinates, Magnitude, Kedalaman, Wilayah, Potensi
 */
data class Gempa(
    @SerializedName("Tanggal")
    val tanggal: String = "",

    @SerializedName("Jam")
    val jam: String = "",

    @SerializedName("DateTime")
    val dateTime: String = "",

    @SerializedName("Coordinates")
    val coordinates: String = "",

    @SerializedName("Lintang")
    val lintang: String = "",

    @SerializedName("Bujur")
    val bujur: String = "",

    @SerializedName("Magnitude")
    val magnitude: String = "",

    @SerializedName("Kedalaman")
    val kedalaman: String = "",

    @SerializedName("Wilayah")
    val wilayah: String = "",

    @SerializedName("Potensi")
    val potensi: String = ""
)

/**
 * Extension functions memanfaatkan fitur Kotlin sesuai persyaratan soal:
 */
fun Gempa.getMagnitudeValue(): Double {
    return magnitude.toDoubleOrNull() ?: 0.0
}

enum class MagnitudeCategory {
    RINGAN,
    SEDANG,
    KUAT
}

fun Gempa.getMagnitudeCategory(): MagnitudeCategory {
    val value = getMagnitudeValue()
    return when {
        value >= 6.0 -> MagnitudeCategory.KUAT
        value >= 5.0 -> MagnitudeCategory.SEDANG
        else -> MagnitudeCategory.RINGAN
    }
}
