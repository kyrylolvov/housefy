package ca.quantum.quants.it.housefy.models

import kotlinx.serialization.Serializable

@Serializable
data class EnvironmentData(
    val temperature: Float,
    val co2: Float,
    val voc: Float,
    val lux: Float,
    val humidity: Float,
    val aqi: Int
)
