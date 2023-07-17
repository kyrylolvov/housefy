package ca.quantum.quants.it.housefy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ca.quantum.quants.it.housefy.ui.theme.HousefyTheme

val LightOnAmbient = compositionLocalOf<MutableState<Boolean>> { error("No light state provided") }
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isLightOn = remember { mutableStateOf(false) }

            HousefyTheme() {
                CompositionLocalProvider(LightOnAmbient provides isLightOn) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Navigation()
                    }
                }
            }
        }
    }
}