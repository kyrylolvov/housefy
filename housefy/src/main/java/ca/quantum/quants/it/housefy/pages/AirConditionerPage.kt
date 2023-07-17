package ca.quantum.quants.it.housefy.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SignalCellularAlt
import androidx.compose.material.icons.rounded.SignalCellularAlt1Bar
import androidx.compose.material.icons.rounded.SignalCellularAlt2Bar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ca.quantum.quants.it.housefy.AirConditionerAmbient
import ca.quantum.quants.it.housefy.components.air_continioner.AirConditionerGraph
import ca.quantum.quants.it.housefy.components.air_continioner.FanSpeedCard
import ca.quantum.quants.it.housefy.components.common.EnergyUsage
import ca.quantum.quants.it.housefy.components.common.StateSwitcher

@Composable
fun AirConditionerPage() {
    val isAirConditionerOn = AirConditionerAmbient.current
    val energyUsageText = remember { mutableStateOf("0.8 kWh  ($0.10/h)") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF0F2F1)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AirConditionerGraph(
            indicatorValue = 25,
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            FanSpeedCard(
                icon = Icons.Rounded.SignalCellularAlt1Bar,
                text = "Low speed",
                modifier = Modifier.weight(1f),
                onClick = {
                    energyUsageText.value = "0.8 kWh  ($0.10/h)"
                } // Update energy usage text on click
            )
            Spacer(modifier = Modifier.width(16.dp))
            FanSpeedCard(
                icon = Icons.Rounded.SignalCellularAlt2Bar,
                text = "Medium speed",
                modifier = Modifier.weight(1f),
                onClick = {
                    energyUsageText.value = "1.2 kWh  ($0.14/h)"
                } // Update energy usage text on click
            )
            Spacer(modifier = Modifier.width(16.dp))
            FanSpeedCard(
                icon = Icons.Rounded.SignalCellularAlt,
                text = "High speed",
                modifier = Modifier.weight(1f),
                onClick = {
                    energyUsageText.value = "1.5 kWh  ($0.18/h)"
                } // Update energy usage text on click
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 100.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            EnergyUsage(
                text = energyUsageText.value,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(24.dp))

            StateSwitcher(
                text = "Toggle on/off",
                checked = isAirConditionerOn.value,
                onCheckedChange = { isChecked -> isAirConditionerOn.value = isChecked },
                modifier = Modifier.weight(1f)
            )
        }
    }
}