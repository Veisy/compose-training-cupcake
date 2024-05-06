package com.example.cupcake.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderUiState
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

@Composable
fun CupcakeNavHost(
    navController: NavHostController,
    uiState: OrderUiState,
    modifier: Modifier,
    onSetFlavor: (String) -> Unit,
    onSetDate: (String) -> Unit,
    onSetQuantity: (Int) -> Unit,
    onCancel: () -> Unit,
    onShareOrder: (String, String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = CupcakeRoutes.Start.name,
        modifier = modifier
    ) {
        composable(CupcakeRoutes.Start.name) {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = {
                    onSetQuantity(it)
                    navController.navigate(CupcakeRoutes.Flavor.name)
                }
            )
        }
        composable(CupcakeRoutes.Flavor.name) {
            SelectOptionScreen(
                subtotal = uiState.price,
                options = DataSource.flavors.map { id -> stringResource(id = id) },
                selectedValue = uiState.flavor,
                onSelectionChanged = onSetFlavor,
                onNextButtonClicked = { navController.navigate(CupcakeRoutes.Pickup.name) },
                onCancelButtonClicked = onCancel
            )
        }
        composable(CupcakeRoutes.Pickup.name) {
            SelectOptionScreen(
                subtotal = uiState.price,
                options = uiState.pickupOptions,
                selectedValue = uiState.date,
                onSelectionChanged = onSetDate,
                onNextButtonClicked = { navController.navigate(CupcakeRoutes.Summary.name) },
                onCancelButtonClicked = onCancel
            )
        }
        composable(CupcakeRoutes.Summary.name) {
            OrderSummaryScreen(
                orderUiState = uiState,
                onSendButtonClicked = onShareOrder,
                onCancelButtonClicked = onCancel,
            )
        }
    }
}
