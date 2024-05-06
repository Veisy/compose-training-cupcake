package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cupcake.R
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderUiState
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent() {
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subtotal = "$100"

        composeTestRule.setContent {
            // When SelectOptionScreen is loaded
            SelectOptionScreen(subtotal = subtotal, options = flavors, selectedValue = "")
        }

        composeTestRule.apply {
            // Then all the options are displayed on the screen.
            flavors.forEach { flavor ->
                onNodeWithText(flavor).assertIsDisplayed()
            }

            // And then the subtotal is displayed correctly.
            onNodeWithText(
                activity.getString(
                    R.string.subtotal_price,
                    subtotal
                )
            ).assertIsDisplayed()

            // And then the next button is disabled
            onNodeWithStringId(R.string.next).assertIsNotEnabled()
        }
    }

    @Test
    fun selectOptionScreen_verifySelectedOption() {
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subtotal = "$100"

        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors, selectedValue = flavors[0])
        }

        composeTestRule.apply {
            onNodeWithText(flavors[0]).assertIsSelected()
            onNodeWithStringId(R.string.next).assertIsEnabled()
        }
    }

    @Test
    fun startScreen_verifyContent() {
        val quantityOptions = listOf(
            Pair(R.string.one_cupcake, 1),
            Pair(R.string.six_cupcakes, 6),
            Pair(R.string.twelve_cupcakes, 12)
        )

        composeTestRule.setContent {
            // When StartOrderScreen is loaded
            StartOrderScreen(quantityOptions = quantityOptions, onNextButtonClicked = {})
        }

        composeTestRule.apply {
            // Then all the quantity options are displayed on the screen.
            quantityOptions.forEach { option ->
                onNodeWithText(activity.getString(option.first)).assertIsDisplayed()
            }
        }
    }

    @Test
    fun summaryScreen_verifyContent() {
        val orderUiState = OrderUiState(
            quantity = 6,
            flavor = "Chocolate",
            date = OrderViewModel().pickupOptions()[0],
            price = "$100",
        )

        composeTestRule.setContent {
            // When OrderSummaryScreen is loaded
            OrderSummaryScreen(
                orderUiState = orderUiState,
                onSendButtonClicked = { _, _ -> },
                onCancelButtonClicked = {})
        }

        composeTestRule.apply {
            // Then all the order details are displayed on the screen.
            onNodeWithText(orderUiState.flavor).assertIsDisplayed()
            onNodeWithText(orderUiState.date).assertIsDisplayed()
            onNodeWithText(
                activity.resources.getQuantityString(
                    R.plurals.cupcakes,
                    orderUiState.quantity,
                    orderUiState.quantity
                )
            ).assertIsDisplayed()

            onNodeWithText(
                activity.getString(
                    R.string.subtotal_price,
                    orderUiState.price
                )
            ).assertIsDisplayed()
        }
    }
}
