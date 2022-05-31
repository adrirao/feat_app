package com.unlam.feat.presentation.component.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.ui.graphics.vector.ImageVector

data class FloatingButtonNavigation(
    val route : String? = null,
    val icon : ImageVector = Icons.Outlined.Add,
    val description : String = ""
)
