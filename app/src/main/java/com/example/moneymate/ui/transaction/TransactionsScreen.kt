package com.example.moneymate.ui.transaction

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneymate.R
import com.example.moneymate.ui.components.MoneyMateBottomBar
import com.example.moneymate.ui.components.PrimaryDestination
import com.example.moneymate.ui.theme.*

@Composable
fun TransactionsScreen(
    selectedTab: Int,
    onSelectTab: (Int) -> Unit,
    onBack: () -> Unit,
    onHome: () -> Unit,
    onAddTransaction: () -> Unit,
    onStatistics: () -> Unit = {},
    onSettings: () -> Unit = {}
) {
    Scaffold(
        containerColor = PageBackground,
        bottomBar = {
            MoneyMateBottomBar(PrimaryDestination.Transactions, onHome,
                onTransactions = {}, onAddTransaction = onAddTransaction,
                onStatistics = onStatistics, onSettings = onSettings)
        }
    ) { insets ->
        Box(Modifier.fillMaxSize().padding(insets), contentAlignment = Alignment.TopCenter) {
            LazyColumn(
                modifier = Modifier.widthIn(max = 600.dp).fillMaxWidth().clipToBounds().testTag("transactions_list"),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item(key = "header") {
                    Row(Modifier.fillMaxWidth().heightIn(min = 56.dp).padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBack) {
                            ListIcon(R.drawable.ic_arrow_back, stringResource(R.string.back))
                        }
                        Text(stringResource(R.string.transactions), fontSize = 20.sp,
                            fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp))
                    }
                }
                item(key = "controls") {
                    Column(Modifier.padding(horizontal = 20.dp)) {
                        Surface(color = Color.White.copy(alpha = .65f), shape = RoundedCornerShape(16.dp)) {
                            Row(Modifier.fillMaxWidth().heightIn(min = 48.dp).padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                ListIcon(R.drawable.ic_search, tint = MutedInk)
                                Spacer(Modifier.width(12.dp))
                                Text(stringResource(R.string.transactions_search), fontSize = 14.sp, color = MutedInk)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        PreviewFilters(selectedTab, onSelectTab)
                        Spacer(Modifier.height(16.dp))
                        MonthSummary()
                        Spacer(Modifier.height(8.dp))
                    }
                }
                items(sampleTransactions, key = { it.category }) { transaction ->
                    SampleTransactionRow(transaction)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PreviewFilters(selectedTab: Int, onSelectTab: (Int) -> Unit) {
    FlowRow(
        Modifier.fillMaxWidth().selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        listOf(R.string.transactions_all, R.string.transaction_expense, R.string.transaction_income)
            .forEachIndexed { index, label ->
                Box(
                    Modifier.heightIn(min = 48.dp).clip(CircleShape)
                        .selectable(selectedTab == index, role = Role.Tab, onClick = { onSelectTab(index) }),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        color = if (selectedTab == index) SoftPurple else Color.Transparent,
                        shape = CircleShape,
                        border = if (selectedTab == index) null else BorderStroke(1.dp, FieldBorder)
                    ) {
                        Text(stringResource(label), modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            fontSize = 14.sp, color = if (selectedTab == index) PrimaryPurple else Ink,
                            fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal)
                    }
                }
            }
        IconButton(onClick = {}) {
            ListIcon(R.drawable.ic_filter, stringResource(R.string.transactions_filter))
        }
    }
}

@Composable
private fun MonthSummary() {
    val largeText = LocalDensity.current.fontScale > 1.3f
    if (largeText) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            MonthTitle()
            SummaryAmounts()
        }
    } else {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            MonthTitle()
            SummaryAmounts()
        }
    }
}

@Composable
private fun MonthTitle() {
    Text(stringResource(R.string.transactions_month), fontSize = 16.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun SummaryAmounts() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(stringResource(R.string.transactions_income_total), color = IncomeGreen,
            fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        Text(stringResource(R.string.transactions_expense_total), color = ExpenseRed,
            fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

private data class SampleTransaction(
    @param:StringRes val category: Int,
    @param:StringRes val time: Int,
    @param:StringRes val amount: Int,
    @param:DrawableRes val icon: Int,
    val badge: Color,
    val tint: Color,
    val income: Boolean = false
)

private val sampleTransactions = listOf(
    SampleTransaction(R.string.food, R.string.transactions_today, R.string.transactions_food_amount,
        R.drawable.ic_food, SoftOrange, FoodOrange),
    SampleTransaction(R.string.transactions_transport, R.string.transactions_yesterday, R.string.transactions_transport_amount,
        R.drawable.ic_car, SoftBlue, TransportBlue),
    SampleTransaction(R.string.transactions_salary, R.string.transactions_salary_date, R.string.transactions_salary_amount,
        R.drawable.ic_salary, SoftGreen, SalaryGreen, income = true),
    SampleTransaction(R.string.transactions_shopping, R.string.transactions_shopping_date, R.string.transactions_shopping_amount,
        R.drawable.ic_shopping, SoftPink, ShoppingPink)
)

@Composable
private fun SampleTransactionRow(transaction: SampleTransaction) {
    val largeText = LocalDensity.current.fontScale > 1.3f
    Column(Modifier.padding(horizontal = 20.dp)) {
        Row(Modifier.fillMaxWidth().heightIn(min = 68.dp).padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(transaction.badge),
                contentAlignment = Alignment.Center) {
                ListIcon(transaction.icon, tint = transaction.tint)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(stringResource(transaction.category), fontSize = 15.sp, lineHeight = 20.sp, fontWeight = FontWeight.SemiBold)
                Text(stringResource(transaction.time), fontSize = 12.sp, lineHeight = 18.sp, color = MutedInk)
                if (largeText) TransactionAmount(transaction)
            }
            if (!largeText) {
                Spacer(Modifier.width(8.dp))
                TransactionAmount(transaction)
            }
        }
        HorizontalDivider(color = TrackLavender.copy(alpha = .5f))
    }
}

@Composable
private fun TransactionAmount(transaction: SampleTransaction) {
    Text(stringResource(transaction.amount), fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
        color = if (transaction.income) IncomeGreen else ExpenseRed)
}

@Composable
private fun ListIcon(@DrawableRes icon: Int, description: String? = null, tint: Color = Ink) {
    Icon(painterResource(icon), description, modifier = Modifier.size(24.dp), tint = tint)
}

@Preview(name = "Phone 360", widthDp = 360, heightDp = 640, showSystemUi = true)
@Preview(name = "Landscape", widthDp = 720, heightDp = 360, showSystemUi = true)
@Preview(name = "Large text", widthDp = 360, heightDp = 800, fontScale = 2f)
@Composable
private fun TransactionsPreview() {
    MoneyMateTheme { TransactionsScreen(0, {}, {}, {}, {}) }
}
