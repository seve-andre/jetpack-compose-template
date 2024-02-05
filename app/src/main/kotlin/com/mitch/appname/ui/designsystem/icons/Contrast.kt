package com.mitch.appname.ui.designsystem.icons/* This ImageVector was generated using Composables – https://www.composables.com */

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private var _Contrast: ImageVector? = null

val ContrastIcon: ImageVector
    get() {
        if (_Contrast != null) {
            return _Contrast!!
        }
        _Contrast = ImageVector.Builder(
            name = "Contrast",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 22f)
                curveToRelative(5.52f, 0f, 10f, -4.48f, 10f, -10f)
                reflectiveCurveTo(17.52f, 2f, 12f, 2f)
                reflectiveCurveTo(2f, 6.48f, 2f, 12f)
                reflectiveCurveTo(6.48f, 22f, 12f, 22f)
                close()
                moveTo(13f, 4.07f)
                curveToRelative(3.94f, 0.49f, 7f, 3.85f, 7f, 7.93f)
                reflectiveCurveToRelative(-3.05f, 7.44f, -7f, 7.93f)
                verticalLineTo(4.07f)
                close()
            }
        }.build()
        return _Contrast!!
    }

@Preview
@Composable
private fun ContrastPreview() {
    Icon(
        imageVector = ContrastIcon,
        contentDescription = null
    )
}
