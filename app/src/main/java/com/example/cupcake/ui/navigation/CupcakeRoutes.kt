package com.example.cupcake.ui.navigation

import androidx.annotation.StringRes
import com.example.cupcake.R

enum class CupcakeRoutes(@StringRes val title: Int) {
    Start(R.string.app_name),
    Flavor(R.string.choose_flavor),
    Pickup(R.string.choose_pickup_date),
    Summary(R.string.order_summary),
}