package com.example.bball.models

import androidx.compose.ui.unit.Dp

data class ColumnSpec<T>(
    val header: String,
    val width: Dp,
    val value: (T) -> String,
    val alignCenter: Boolean = true
)