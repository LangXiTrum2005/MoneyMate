package com.example.moneymate

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransactionsFlowTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    private fun openList() {
        compose.onNodeWithText("Giao dịch").performClick()
        compose.onNodeWithText("Tất cả").assertIsSelected()
    }

    private fun reveal(text: String): SemanticsNodeInteraction {
        compose.onNodeWithTag("transactions_list").performScrollToNode(hasText(text))
        return compose.onNodeWithText(text)
    }

    @Test fun referenceContentAndStaticControls() {
        openList()
        compose.onNodeWithTag("bottom_transactions").assertIsSelected()
        listOf("Tất cả", "Chi tiêu", "Thu nhập").forEach { tab ->
            reveal(tab).performClick().assertIsSelected()
            listOf("Ăn uống", "Di chuyển", "Lương", "Mua sắm",
                "-80.000 đ", "-50.000 đ", "+15.000.000 đ", "-300.000 đ").forEach {
                reveal(it).assertIsDisplayed()
            }
            reveal("Thu: +15.000.000 đ").assertIsDisplayed()
            compose.onNodeWithText("Chi: -2.550.000 đ").assertIsDisplayed()
        }
        reveal("Tìm kiếm giao dịch...").performClick()
        compose.onAllNodes(hasSetTextAction()).assertCountEquals(0)
        compose.onNodeWithContentDescription("Lọc giao dịch").performClick()
        reveal("Ăn uống").performClick()
        compose.onNodeWithTag("transactions_list").performScrollToIndex(0)
        compose.onNodeWithContentDescription("Quay lại").performClick()
        compose.onNodeWithText("12.450.000 đ").assertIsDisplayed()
        openList()
        pressBack()
        compose.onNodeWithText("12.450.000 đ").assertIsDisplayed()
    }

    @Test fun selectionAndOriginSurviveRecreationAndBothBackPaths() {
        openList()
        compose.onNodeWithText("Thu nhập").performClick()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Thu nhập").assertIsSelected()
        repeat(2) { attempt ->
            compose.onNodeWithContentDescription("Thêm giao dịch").performClick()
            compose.onNodeWithText("Lưu giao dịch").assertIsDisplayed()
            compose.onNodeWithTag("bottom_transactions").assertDoesNotExist()
            compose.activityRule.scenario.recreate()
            if (attempt == 0) compose.onNodeWithContentDescription("Quay lại").performClick()
            else pressBack()
            compose.onNodeWithText("Thu nhập").assertIsSelected()
            reveal("Mua sắm").assertIsDisplayed()
        }
        compose.onNodeWithText("Trang chủ").performClick()
        openList()
        compose.onNodeWithText("Trang chủ").performClick()
        compose.onNodeWithContentDescription("Thêm giao dịch").performClick()
        pressBack()
        compose.onNodeWithText("12.450.000 đ").assertIsDisplayed()
    }
}
