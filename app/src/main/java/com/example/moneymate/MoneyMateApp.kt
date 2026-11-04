package com.example.moneymate

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import com.example.moneymate.ui.home.HomeScreen
import com.example.moneymate.ui.statistics.StatisticsScreen
import com.example.moneymate.ui.settings.SettingsScreen
import com.example.moneymate.ui.transaction.AddTransactionScreen
import com.example.moneymate.ui.transaction.TransactionsScreen

@Composable
fun MoneyMateApp() {
    var showAddTransaction by rememberSaveable { mutableStateOf(false) }
    var showTransactions by rememberSaveable { mutableStateOf(false) }
    var showStatistics by rememberSaveable { mutableStateOf(false) }
    var showSettings by rememberSaveable { mutableStateOf(false) }
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val statisticsState = rememberSaveableStateHolder()

    val goHome: () -> Unit = {
        showTransactions = false
        showStatistics = false
        showSettings = false
        selectedTab = 0
    }
    val goBack: () -> Unit = {
        when {
            showAddTransaction -> showAddTransaction = false
            showSettings -> showSettings = false
            else -> goHome()
        }
    }

    val goStatistics: () -> Unit = { showTransactions = false; showStatistics = true }
    val goTransactions: () -> Unit = { showStatistics = false; showTransactions = true }

    val goSettings: () -> Unit = { showSettings = true }

    BackHandler(enabled = showAddTransaction || showTransactions || showStatistics || showSettings, onBack = goBack)

    if (showAddTransaction) {
        AddTransactionScreen(onBack = goBack)
    } else if (showSettings) {
        SettingsScreen(onBack = goBack)
    } else if (showStatistics) {
        statisticsState.SaveableStateProvider("statistics") {
            StatisticsScreen(onBack = goHome, onHome = goHome, onTransactions = goTransactions,
                onAddTransaction = { showAddTransaction = true }, onSettings = goSettings)
        }
    } else if (showTransactions) {
        TransactionsScreen(
            selectedTab = selectedTab,
            onSelectTab = { selectedTab = it },
            onBack = goHome,
            onHome = goHome,
            onAddTransaction = { showAddTransaction = true },
            onStatistics = goStatistics,
            onSettings = goSettings
        )
    } else {
        HomeScreen(onAddTransaction = { showAddTransaction = true },
            onTransactions = goTransactions, onStatistics = goStatistics, onSettings = goSettings)
    }
}
