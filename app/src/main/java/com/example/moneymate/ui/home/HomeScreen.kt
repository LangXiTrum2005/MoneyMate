package com.example.moneymate.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneymate.ui.components.MoneyMateBottomBar
import com.example.moneymate.ui.components.PrimaryDestination
import com.example.moneymate.R
import com.example.moneymate.ui.theme.*

@Composable
fun HomeScreen(onAddTransaction: () -> Unit = {}, onTransactions: () -> Unit = {}) {
    Scaffold(
        containerColor = PageBackground,
        bottomBar = {
            MoneyMateBottomBar(PrimaryDestination.Home, onHome = {},
                onTransactions = onTransactions, onAddTransaction = onAddTransaction)
        }
    ) { insets ->
        Box(Modifier.fillMaxSize().padding(insets), contentAlignment = Alignment.TopCenter) {
            Column(
                Modifier.widthIn(max = 600.dp).fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp).padding(bottom = 24.dp)
            ) {
                HomeHeader()
                Spacer(Modifier.height(12.dp))
                Text(stringResource(R.string.greeting), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(stringResource(R.string.greeting_subtitle), color = MutedInk, fontSize = 14.sp)
                Spacer(Modifier.height(16.dp))
                BalanceCard()
                Spacer(Modifier.height(16.dp))
                BudgetCard()
                Spacer(Modifier.height(8.dp))
                RecentTransactions()
            }
        }
    }
}

@Composable
private fun HomeHeader() {
    Row(Modifier.fillMaxWidth().heightIn(min = 56.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(30.dp).clip(RoundedCornerShape(8.dp))
                .background(Brush.linearGradient(listOf(PrimaryPurple, GradientBlue))),
            contentAlignment = Alignment.Center
        ) {
            HomeIcon(R.drawable.ic_logo, tint = Color.White, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.width(8.dp))
        Text(stringResource(R.string.app_name), fontSize = 20.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f))
        IconButton(onClick = {}) {
            HomeIcon(R.drawable.ic_bell, description = R.string.notifications)
        }
    }
}

@Composable
private fun HomeCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        shape = RoundedCornerShape(20.dp), color = Color.White,
        shadowElevation = 2.dp, modifier = Modifier.fillMaxWidth()
    ) { Column(content = content) }
}

@Composable
private fun BalanceCard() {
    HomeCard {
        Row(
            Modifier.fillMaxWidth()
                .background(Brush.linearGradient(listOf(PrimaryPurple, GradientBlue)))
                .padding(start = 18.dp, end = 8.dp, top = 14.dp, bottom = 14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(Modifier.weight(1f)) {
                Text(stringResource(R.string.current_balance), fontSize = 14.sp, lineHeight = 20.sp, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text(stringResource(R.string.balance_amount), fontSize = 30.sp,
                    lineHeight = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            IconButton(onClick = {}) {
                HomeIcon(R.drawable.ic_eye, description = R.string.balance_visibility, tint = Color.White)
            }
        }
        if (LocalDensity.current.fontScale > 1.3f) {
            Column(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                BalanceTotal(R.string.total_income, R.string.income_amount, IncomeGreen, Modifier.fillMaxWidth())
                HorizontalDivider(color = PageBackground)
                BalanceTotal(R.string.total_expense, R.string.expense_amount, ExpenseRed, Modifier.fillMaxWidth())
            }
        } else {
            Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min).padding(vertical = 12.dp)) {
                BalanceTotal(R.string.total_income, R.string.income_amount, IncomeGreen, Modifier.weight(1f))
                VerticalDivider(color = PageBackground)
                BalanceTotal(R.string.total_expense, R.string.expense_amount, ExpenseRed, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun BalanceTotal(@StringRes title: Int, @StringRes amount: Int, color: Color, modifier: Modifier) {
    Column(modifier.padding(horizontal = 18.dp)) {
        Text(stringResource(title), fontSize = 14.sp, lineHeight = 20.sp, color = MutedInk)
        Spacer(Modifier.height(4.dp))
        Text(stringResource(amount), fontSize = 16.sp, lineHeight = 22.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
private fun FoodBadge(background: Color, tint: Color) {
    Box(Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(background),
        contentAlignment = Alignment.Center) {
        HomeIcon(R.drawable.ic_food, tint = tint)
    }
}

@Composable
private fun BudgetCard() {
    val largeText = LocalDensity.current.fontScale > 1.3f
    HomeCard {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FoodBadge(SoftPurple, BudgetPink)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(stringResource(R.string.budget_title), fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    if (!largeText) {
                        Spacer(Modifier.height(6.dp))
                        Text(stringResource(R.string.budget_remaining), fontSize = 11.sp, color = MutedInk)
                    }
                }
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.budget_percentage), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            if (largeText) {
                Spacer(Modifier.height(8.dp))
                Text(stringResource(R.string.budget_remaining), fontSize = 11.sp, color = MutedInk)
            }
            Spacer(Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { .75f }, color = ProgressGreen, trackColor = TrackLavender,
                modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape),
                gapSize = 0.dp, drawStopIndicator = {}
            )
        }
    }
}

@Composable
private fun RecentTransactions() {
    val largeText = LocalDensity.current.fontScale > 1.3f
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.recent_transactions), fontSize = 16.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        TextButton(onClick = {}, contentPadding = PaddingValues(start = 8.dp)) {
            Text(stringResource(R.string.view_all), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
    HomeCard {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            FoodBadge(SoftOrange, FoodOrange)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(stringResource(R.string.food), fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Bold)
                Text(stringResource(R.string.transaction_time), fontSize = 12.sp, lineHeight = 18.sp, color = MutedInk)
                if (largeText) {
                    Spacer(Modifier.height(4.dp))
                    Text(stringResource(R.string.transaction_amount), fontSize = 14.sp,
                        fontWeight = FontWeight.Bold, color = ExpenseRed)
                }
            }
            Spacer(Modifier.width(8.dp))
            if (!largeText) {
                Text(stringResource(R.string.transaction_amount), fontSize = 14.sp,
                    fontWeight = FontWeight.Bold, color = ExpenseRed)
            }
        }
    }
}

@Composable
private fun HomeIcon(@DrawableRes icon: Int, modifier: Modifier = Modifier,
    @StringRes description: Int? = null, tint: Color = Ink) {
    Icon(painterResource(icon), description?.let { stringResource(it) }, modifier.size(24.dp), tint)
}

@Preview(name = "Phone 360", widthDp = 360, heightDp = 640, showSystemUi = true)
@Preview(name = "Phone 412", widthDp = 412, heightDp = 820, showSystemUi = true)
@Preview(name = "Landscape", widthDp = 720, heightDp = 360, showSystemUi = true)
@Preview(name = "Large text", widthDp = 360, heightDp = 800, fontScale = 2f)
@Composable
private fun HomePreview() {
    MoneyMateTheme { HomeScreen() }
}
