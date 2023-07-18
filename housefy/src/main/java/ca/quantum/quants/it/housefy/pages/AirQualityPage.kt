package ca.quantum.quants.it.housefy.pages

/*
 * @author Artem Tsurkan, n01414146
 * @author Wenyuan Yu, n01403697
 * @author Kyrylo Lvov, n01414058
 * @course Software Project - CENG-322-0NA
 */

import android.util.Log
import androidx.compose.material3.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.quantum.quants.it.housefy.R
import ca.quantum.quants.it.housefy.components.air_quality.AQICategory
import ca.quantum.quants.it.housefy.components.air_quality.AQICategoryCard
import ca.quantum.quants.it.housefy.components.air_quality.AirQualityGraph
import ca.quantum.quants.it.housefy.components.air_quality.getAQIColor
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.get
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.lang.Math.max
import kotlin.math.roundToInt

@Composable
fun AirQualityPage() {
    val aqiCategories =
        listOf(
            AQICategory(
                stringResource(R.string.excellent),
                stringResource(R.string.excellent_description),
                Color(0xFF8CD456),
            ),
            AQICategory(
                stringResource(R.string.good),
                stringResource(R.string.good_description),
                Color(0xFFFFE24C),
            ),
            AQICategory(
                stringResource(R.string.moderate),
                stringResource(R.string.moderate_description),
                Color(0xFFFFA500),
            ),
            AQICategory(
                stringResource(R.string.poor),
                stringResource(R.string.poor_description),
                Color(0xFFFF0000),
            ),
        )

    var environmentDataList by remember { mutableStateOf(emptyList<EnvironmentData>()) }
    var currentDataIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            try {
                val newData = fetchEnvironmentData()
                if (newData != null && newData.isNotEmpty()) {
                    environmentDataList = newData
                    currentDataIndex = (currentDataIndex + 1) % environmentDataList.size
                }
            } catch (e: Exception) {
                Log.e("Air Quality", "Error during fetching air quality")
            }
            delay(3000)
        }
    }

    val value = environmentDataList.getOrNull(currentDataIndex)?.let {
        calculateAQI(it.co2).coerceAtMost(100)
    } ?: 0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF0F2F1))
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AirQualityGraph(
                indicatorValue = value,
                foregroundIndicatorColor = getAQIColor(value)
            )
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        items(aqiCategories) { category ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                AQICategoryCard(category)
            }
        }

    }
}

@Serializable
data class EnvironmentData(
    val temperature: Float,
    val co2: Float,
    val voc: Float,
    val lightLevel: Float,
)

val client = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 3000
    }
}

suspend fun fetchEnvironmentData(): List<EnvironmentData>? = try {
    withContext(Dispatchers.IO) {
        client.get("https://housefybackend.azurewebsites.net/api/environment")
    }
} catch (e: Exception) {
    null
}

fun calculateAQI(co2: Float): Int {
    return (co2 / 10).roundToInt()
}