package com.shariarunix.composestripeimplementation.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Heart: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Heart",
        defaultWidth = 512.dp,
        defaultHeight = 512.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(17.5f, 1.917f)
            arcToRelative(6.4f, 6.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -5.5f, 3.3f)
            arcToRelative(6.4f, 6.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -5.5f, -3.3f)
            arcTo(6.8f, 6.8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 8.967f)
            curveToRelative(0f, 4.547f, 4.786f, 9.513f, 8.8f, 12.88f)
            arcToRelative(4.974f, 4.974f, 0f, isMoreThanHalf = false, isPositiveArc = false, 6.4f, 0f)
            curveTo(19.214f, 18.48f, 24f, 13.514f, 24f, 8.967f)
            arcTo(6.8f, 6.8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 17.5f, 1.917f)
            close()
        }
    }.build()
}
