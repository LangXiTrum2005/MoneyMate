package com.example.moneymate

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddTransactionFlowTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun tabsSurviveRecreationAndResetOnReopen() {
        compose.onNodeWithContentDescription("Thêm giao dịch").performClick()
        compose.onNodeWithText("Chi tiêu").assertIsSelected()
        compose.onNodeWithText("Thu nhập").performClick().assertIsSelected()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Thu nhập").assertIsSelected()
        compose.onNodeWithText("150.000 đ", substring = true).assertIsDisplayed()
        compose.onNodeWithText("Hôm nay, 20/01/2025", substring = true).assertIsDisplayed()
        compose.onNodeWithText("Ăn trưa với bạn", substring = true).performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Lưu giao dịch").performClick()
        compose.onNodeWithText("Thu nhập").assertIsSelected()
        compose.onNodeWithContentDescription("Quay lại").performClick()
        compose.onNodeWithText("12.450.000 đ").assertIsDisplayed()
        compose.onNodeWithContentDescription("Thêm giao dịch").performClick()
        compose.onNodeWithText("Chi tiêu").assertIsSelected()
        pressBack()
        compose.onNodeWithText("12.450.000 đ").assertIsDisplayed()
    }
}
