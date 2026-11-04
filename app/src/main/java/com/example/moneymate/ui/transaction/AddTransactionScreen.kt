package com.example.moneymate.ui.transaction

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneymate.R
import com.example.moneymate.ui.theme.*

@Composable
fun AddTransactionScreen(onBack: () -> Unit) {
    var incomeSelected by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        containerColor = PageBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                Row(
                    Modifier.widthIn(max = 600.dp).fillMaxWidth()
                        .padding(start = 8.dp, end = 20.dp).heightIn(min = 56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        TransactionIcon(R.drawable.ic_arrow_back, stringResource(R.string.back))
                    }
                    Spacer(Modifier.width(4.dp))
                    Text(
                        stringResource(R.string.add_transaction),
                        fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        bottomBar = {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = { /* Static mockup: intentionally does not save. */ },
                    modifier = Modifier.widthIn(max = 600.dp).fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp).heightIn(min = 48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(stringResource(R.string.save_transaction), fontSize = 16.sp)
                }
            }
        }
    ) { insets ->
        Box(Modifier.fillMaxSize().padding(insets), contentAlignment = Alignment.TopCenter) {
            Column(
                Modifier.widthIn(max = 600.dp).fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp).padding(top = 8.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TransactionTabs(incomeSelected, onSelectIncome = { incomeSelected = it })
                StaticField(stringResource(R.string.transaction_amount_label), required = true) {
                    Text(stringResource(R.string.transaction_sample_amount), fontWeight = FontWeight.SemiBold)
                }
                StaticField(stringResource(R.string.transaction_category_label), required = true) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(SoftOrange),
                            contentAlignment = Alignment.Center
                        ) {
                            TransactionIcon(R.drawable.ic_food, tint = FoodOrange)
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(stringResource(R.string.food), modifier = Modifier.weight(1f))
                        TransactionIcon(R.drawable.ic_chevron_down)
                    }
                }
                StaticField(stringResource(R.string.transaction_date_label)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TransactionIcon(R.drawable.ic_calendar)
                        Spacer(Modifier.width(12.dp))
                        Text(stringResource(R.string.transaction_sample_date))
                    }
                }
                StaticField(stringResource(R.string.transaction_note_label), multiline = true) {
                    Text(stringResource(R.string.transaction_sample_note))
                }
            }
        }
    }
}

@Composable
private fun TransactionTabs(incomeSelected: Boolean, onSelectIncome: (Boolean) -> Unit) {
    Surface(
        shape = RoundedCornerShape(28.dp), color = PageBackground,
        border = BorderStroke(1.dp, TrackLavender)
    ) {
        Row(Modifier.fillMaxWidth().selectableGroup().height(IntrinsicSize.Min)) {
            listOf(false to R.string.transaction_expense, true to R.string.transaction_income)
                .forEach { (income, label) ->
                    val selected = incomeSelected == income
                    Box(
                        Modifier.weight(1f).fillMaxHeight().heightIn(min = 48.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(if (selected) SoftPurple else Color.Transparent)
                            .selectable(selected = selected, role = Role.Tab, onClick = { onSelectIncome(income) })
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(label), fontSize = 14.sp,
                            color = if (selected) PrimaryPurple else Ink,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
        }
    }
}

@Composable
private fun StaticField(
    label: String,
    required: Boolean = false,
    multiline: Boolean = false,
    content: @Composable () -> Unit
) {
    Column(Modifier.fillMaxWidth().semantics(mergeDescendants = true) {}) {
        Row {
            Text(label, fontSize = 14.sp, lineHeight = 20.sp)
            if (required) Text(stringResource(R.string.required_marker), color = ExpenseRed, fontSize = 14.sp, lineHeight = 20.sp)
        }
        Spacer(Modifier.height(6.dp))
        Surface(
            shape = RoundedCornerShape(10.dp), color = PageBackground,
            border = BorderStroke(1.dp, FieldBorder), modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                Modifier.heightIn(min = if (multiline) 76.dp else 48.dp)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = if (multiline) Alignment.TopStart else Alignment.CenterStart
            ) {
                ProvideTextStyle(MaterialTheme.typography.bodyMedium.copy(color = Ink, fontSize = 16.sp)) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun TransactionIcon(@DrawableRes icon: Int, description: String? = null, tint: Color = Ink) {
    Icon(painterResource(icon), contentDescription = description, tint = tint, modifier = Modifier.size(24.dp))
}

@Preview(name = "Phone 360", widthDp = 360, heightDp = 640, showSystemUi = true)
@Preview(name = "Phone 412", widthDp = 412, heightDp = 820, showSystemUi = true)
@Preview(name = "Landscape", widthDp = 720, heightDp = 360, showSystemUi = true)
@Preview(name = "Large text", widthDp = 360, heightDp = 800, fontScale = 2f)
@Composable
private fun AddTransactionPreview() {
    MoneyMateTheme { AddTransactionScreen(onBack = {}) }
}
