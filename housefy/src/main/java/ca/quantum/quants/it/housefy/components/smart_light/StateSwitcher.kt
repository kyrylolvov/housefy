package ca.quantum.quants.it.housefy.components.smart_light

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import ca.quantum.quants.it.housefy.LightOnAmbient

@Composable
fun StateSwitcher(modifier: Modifier = Modifier) {
    val isLightOn = LightOnAmbient.current

    Box(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .height(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Switch(
                checked = isLightOn.value,
                onCheckedChange = { isChecked -> isLightOn.value = isChecked },
                colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFF7468E4)),
            )
            Text(
                text = "Toggle on/off",
                style = MaterialTheme.typography.bodySmall
                    .copy(fontWeight = FontWeight.Medium)
                    .copy(color = Color(0xFFA3A3A5)),
            )
        }
    }
}