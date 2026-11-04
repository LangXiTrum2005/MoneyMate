package com.example.moneymate

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.moneymate.ui.home.HomeScreen
import com.example.moneymate.ui.transaction.AddTransactionScreen
import com.example.moneymate.ui.transaction.TransactionsScreen

@Composable
fun MoneyMateApp() {
    var showAddTransaction by rememberSaveable { mutableStateOf(false) }
    var showTransactions by rememberSaveable { mutableStateOf(false) }
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    val goHome: () -> Unit = {
        showTransactions = false
        selectedTab = 0
    }
    val goBack: () -> Unit = {
        if (showAddTransaction) showAddTransaction = false else goHome()
    }

    BackHandler(enabled = showAddTransaction || showTransactions, onBack = goBack)

    if (showAddTransaction) {
        AddTransactionScreen(onBack = goBack)
    } else if (showTransactions) {
        TransactionsScreen(
            selectedTab = selectedTab,
            onSelectTab = { selectedTab = it },
            onBack = goHome,
            onHome = goHome,
            onAddTransaction = { showAddTransaction = true }
        )
    } else {
        HomeScreen(onAddTransaction = { showAddTransaction = true },
            onTransactions = { showTransactions = true })
    }
}
