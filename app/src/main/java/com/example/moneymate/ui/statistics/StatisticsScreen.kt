package com.example.moneymate.ui.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneymate.R
import com.example.moneymate.ui.components.MoneyMateBottomBar
import com.example.moneymate.ui.components.PrimaryDestination
import com.example.moneymate.ui.theme.*

// Shared preview data for all three period tabs until real statistics are connected.
private data class CategoryShare(val label: Int, val percent: Int, val color: Color)
private val categoryShares = listOf(
    CategoryShare(R.string.food, 39, ChartOrange),
    CategoryShare(R.string.transactions_shopping, 20, ChartBlue),
    CategoryShare(R.string.transactions_transport, 12, ChartPurple),
    CategoryShare(R.string.statistics_entertainment, 8, ChartGreen),
    CategoryShare(R.string.statistics_other, 21, ChartGray)
)
private val dailyExpenses = listOf(40, 15, 160, 50, 20, 180, 310, 110, 20, 55, 65, 150, 250, 160, 90, 130, 200, 120, 95, 65, 110)

@Composable
fun StatisticsScreen(
    onBack: () -> Unit,
    onHome: () -> Unit,
    onTransactions: () -> Unit,
    onAddTransaction: () -> Unit,
    onSettings: () -> Unit = {}
) {
    var selectedPeriod by rememberSaveable { mutableIntStateOf(0) }
    var monthOffset by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        containerColor = PageBackground,
        bottomBar = {
            MoneyMateBottomBar(PrimaryDestination.Statistics, onHome, onTransactions, onAddTransaction,
                onSettings = onSettings)
        }
    ) { insets ->
        Box(Modifier.fillMaxSize().padding(insets), contentAlignment = Alignment.TopCenter) {
            Column(
                Modifier.widthIn(max = 600.dp).fillMaxWidth()
                    .verticalScroll(rememberScrollState()).testTag("statistics_content")
                    .padding(bottom = 24.dp)
            ) {
                Row(Modifier.fillMaxWidth().heightIn(min = 56.dp).padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(painterResource(R.drawable.ic_arrow_back), stringResource(R.string.back), tint = Ink)
                    }
                    Text(stringResource(R.string.statistics), fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp))
                }
                Column(Modifier.padding(horizontal = 20.dp)) {
                    PeriodTabs(selectedPeriod) { selectedPeriod = it }
                    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { monthOffset-- }) {
                            Icon(painterResource(R.drawable.ic_chevron_down), stringResource(R.string.statistics_previous),
                                Modifier.size(22.dp).rotate(90f), tint = Ink)
                        }
                        Text(stringResource(R.string.statistics_period, Math.floorMod(monthOffset, 12) + 1,
                            2025 + Math.floorDiv(monthOffset, 12)),
                            Modifier.weight(1f), fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        IconButton(onClick = { monthOffset++ }) {
                            Icon(painterResource(R.drawable.ic_chevron_down), stringResource(R.string.statistics_next),
                                Modifier.size(22.dp).rotate(-90f), tint = Ink)
                        }
                    }
                    CategorySummary()
                    Spacer(Modifier.height(24.dp))
                    DailyExpensesCard(Math.floorMod(monthOffset, 12) + 1)
                }
            }
        }
    }
}

@Composable
private fun PeriodTabs(selected: Int, onSelect: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth().clip(CircleShape).background(Color.White)
        .border(1.dp, TrackLavender, CircleShape).selectableGroup()) {
        listOf(R.string.statistics_month, R.string.statistics_quarter, R.string.statistics_year)
            .forEachIndexed { index, label ->
                Box(Modifier.weight(1f).heightIn(min = 48.dp).clip(CircleShape)
                    .background(if (selected == index) SoftPurple else Color.Transparent)
                    .selectable(selected == index, role = Role.Tab, onClick = { onSelect(index) })
                    .padding(horizontal = 12.dp, vertical = 12.dp), contentAlignment = Alignment.Center) {
                    Text(stringResource(label), fontSize = 14.sp,
                        color = if (selected == index) PrimaryPurple else Ink,
                        fontWeight = if (selected == index) FontWeight.SemiBold else FontWeight.Normal)
                }
            }
    }
}

@Composable
private fun CategorySummary() {
    val largeText = LocalDensity.current.fontScale > 1.3f
    if (largeText) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ExpenseDonut(Modifier.size(220.dp))
            CategoryLegend(Modifier.fillMaxWidth())
        }
    } else {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            ExpenseDonut(Modifier.weight(1f).aspectRatio(1f))
            CategoryLegend(Modifier.weight(1f))
        }
    }
}

@Composable
private fun ExpenseDonut(modifier: Modifier) {
    val description = stringResource(R.string.statistics_chart_description)
    Box(modifier, contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize().semantics { contentDescription = description }) {
            val stroke = size.minDimension * .17f
            val diameter = size.minDimension - stroke
            val origin = Offset((size.width - diameter) / 2, (size.height - diameter) / 2)
            var angle = -90f
            categoryShares.forEach { share ->
                val sweep = share.percent * 3.6f
                drawArc(share.color, angle, sweep, false, origin, Size(diameter, diameter), style = Stroke(stroke))
                angle += sweep
            }
        }
        Column(Modifier.fillMaxWidth(.72f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(R.string.total_expense), fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(4.dp))
            Text(stringResource(R.string.statistics_total), fontSize = 14.sp, fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
private fun CategoryLegend(modifier: Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        categoryShares.forEach { share ->
            Row(Modifier.fillMaxWidth().semantics(mergeDescendants = true) {},
                verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(12.dp).clip(CircleShape).background(share.color))
                Spacer(Modifier.width(8.dp))
                Text(stringResource(share.label), Modifier.weight(1f), fontSize = 13.sp)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.statistics_percent, share.percent), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun DailyExpensesCard(month: Int) {
    val description = stringResource(R.string.statistics_daily_description)
    Surface(shape = RoundedCornerShape(20.dp), color = Color.White,
        modifier = Modifier.fillMaxWidth(), shadowElevation = 1.dp) {
        Column(Modifier.padding(16.dp)) {
            Text(stringResource(R.string.statistics_daily), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth().height(144.dp)) {
                Column(Modifier.width(36.dp).fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                    listOf("400K", "300K", "200K", "100K", "0").forEach {
                        Text(it, fontSize = 10.sp, color = MutedInk, lineHeight = 12.sp)
                    }
                }
                Column(Modifier.weight(1f)) {
                    Canvas(Modifier.fillMaxWidth().weight(1f).semantics { contentDescription = description }) {
                        val baseline = size.height
                        repeat(5) { index ->
                            val y = baseline * index / 4f
                            drawLine(TrackLavender.copy(alpha = .6f), Offset(0f, y), Offset(size.width, y), 1.dp.toPx())
                        }
                        val step = size.width / dailyExpenses.size
                        dailyExpenses.forEachIndexed { index, value ->
                            val height = baseline * value / 400f
                            drawRect(ChartBar, Offset(index * step + step * .2f, baseline - height),
                                Size(step * .6f, height))
                        }
                    }
                    Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        listOf(1, 5, 10, 15, 20).map { "$it/$month" }.forEach {
                            Text(it, fontSize = 10.sp, color = MutedInk)
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Statistics phone", widthDp = 360, heightDp = 640, showSystemUi = true)
@Preview(name = "Statistics landscape", widthDp = 720, heightDp = 360, showSystemUi = true)
@Preview(name = "Statistics large text", widthDp = 360, heightDp = 900, fontScale = 2f)
@Composable
private fun StatisticsPreview() {
    MoneyMateTheme { StatisticsScreen({}, {}, {}, {}) }
}
