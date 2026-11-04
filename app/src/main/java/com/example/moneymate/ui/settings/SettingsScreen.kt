package com.example.moneymate.ui.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneymate.R
import com.example.moneymate.ui.theme.*

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(containerColor = PageBackground) { insets ->
        Box(Modifier.fillMaxSize().padding(insets), contentAlignment = Alignment.TopCenter) {
            Column(Modifier.widthIn(max = 600.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState()).padding(bottom = 24.dp)) {
                Row(Modifier.fillMaxWidth().heightIn(min = 56.dp).padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(painterResource(R.drawable.ic_arrow_back), stringResource(R.string.back), tint = Ink)
                    }
                    Text(stringResource(R.string.settings), fontSize = 20.sp,
                        fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp))
                }
                Surface(Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp), color = Color.White,
                    shadowElevation = 1.dp) {
                    Column {
                        SettingsRow(R.drawable.ic_sun, R.string.settings_appearance, R.string.settings_light)
                        SettingsDivider()
                        SettingsRow(R.drawable.ic_currency, R.string.settings_currency, R.string.settings_vnd)
                        SettingsDivider()
                        SettingsRow(R.drawable.ic_bell, R.string.settings_reminder, R.string.settings_reminder_time, toggle = true)
                        SettingsDivider()
                        SettingsRow(R.drawable.ic_lock, R.string.settings_lock, R.string.settings_lock_method, toggle = true)
                        SettingsDivider()
                        SettingsRow(R.drawable.ic_backup, R.string.settings_backup)
                    }
                }
            }
        }
    }
}

// Switches only update preview UI state; no device preferences or services are changed.
@Composable
private fun SettingsRow(@DrawableRes icon: Int, @StringRes title: Int,
    @StringRes subtitle: Int? = null, toggle: Boolean = false) {
    Row(Modifier.fillMaxWidth().heightIn(min = 64.dp)
        .then(if (toggle) Modifier else Modifier.semantics(mergeDescendants = true) { disabled() })
        .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Icon(painterResource(icon), null, Modifier.size(24.dp), tint = Ink)
        Column(Modifier.weight(1f)) {
            Text(stringResource(title), color = Ink, fontSize = 16.sp,
                lineHeight = 22.sp, fontWeight = FontWeight.Medium)
            subtitle?.let {
                Text(stringResource(it), color = MutedInk, fontSize = 14.sp, lineHeight = 20.sp)
            }
        }
        if (toggle) {
            var checked by rememberSaveable { mutableStateOf(true) }
            val label = stringResource(title)
            Switch(
                checked = checked,
                onCheckedChange = { checked = it },
                modifier = Modifier.semantics { contentDescription = label },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = PrimaryPurple,
                    checkedBorderColor = PrimaryPurple,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = TrackLavender,
                    uncheckedBorderColor = MutedInk
                )
            )
        } else {
            Icon(painterResource(R.drawable.ic_chevron_down), null,
                Modifier.size(20.dp).rotate(-90f), tint = MutedInk)
        }
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(color = PageBackground, thickness = 1.dp)
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Preview(showBackground = true, widthDp = 800, heightDp = 360)
@Composable
private fun SettingsPreview() {
    MoneyMateTheme { SettingsScreen(onBack = {}) }
}
