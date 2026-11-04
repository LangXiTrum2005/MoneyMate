package com.example.moneymate.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneymate.R
import com.example.moneymate.ui.theme.*

enum class PrimaryDestination { Home, Transactions }

@Composable
fun MoneyMateBottomBar(
    selectedDestination: PrimaryDestination,
    onHome: () -> Unit,
    onTransactions: () -> Unit,
    onAddTransaction: () -> Unit
) {
    Surface(color = Color.White, shadowElevation = 8.dp) {
        Box(Modifier.fillMaxWidth().navigationBarsPadding(), contentAlignment = Alignment.Center) {
            Row(
                Modifier.widthIn(max = 600.dp).fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomItem(R.drawable.ic_home, R.string.home, selectedDestination == PrimaryDestination.Home, Modifier.weight(1f), onHome)
                BottomItem(R.drawable.ic_wallet, R.string.transactions, selectedDestination == PrimaryDestination.Transactions, Modifier.weight(1f).testTag("bottom_transactions"), onTransactions)
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    FloatingActionButton(onClick = onAddTransaction, shape = CircleShape,
                        containerColor = PrimaryPurple, contentColor = Color.White,
                        modifier = Modifier.size(52.dp)) {
                        BottomIcon(R.drawable.ic_plus, Modifier.size(30.dp), R.string.add_transaction, Color.White)
                    }
                }
                BottomItem(R.drawable.ic_chart, R.string.statistics, false, Modifier.weight(1f))
                BottomItem(R.drawable.ic_settings, R.string.settings, false, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun BottomItem(@DrawableRes icon: Int, @StringRes label: Int, active: Boolean, modifier: Modifier, onClick: () -> Unit = {}) {
    TextButton(onClick = onClick, modifier = modifier.heightIn(min = 48.dp).semantics { selected = active },
        contentPadding = PaddingValues(vertical = 2.dp), shape = RoundedCornerShape(12.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(width = 44.dp, height = 30.dp)
                .clip(RoundedCornerShape(10.dp)).background(if (active) SoftPurple else Color.Transparent),
                contentAlignment = Alignment.Center) {
                BottomIcon(icon, tint = if (active) PrimaryPurple else MutedInk, modifier = Modifier.size(21.dp))
            }
            Spacer(Modifier.height(2.dp))
            Text(stringResource(label), fontSize = 10.sp, lineHeight = 14.sp,
                color = if (active) PrimaryPurple else MutedInk,
                fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal)
        }
    }
}

@Composable
private fun BottomIcon(@DrawableRes icon: Int, modifier: Modifier = Modifier,
    @StringRes description: Int? = null, tint: Color = Ink) {
    Icon(painterResource(icon), description?.let { stringResource(it) }, modifier.size(24.dp), tint)
}
