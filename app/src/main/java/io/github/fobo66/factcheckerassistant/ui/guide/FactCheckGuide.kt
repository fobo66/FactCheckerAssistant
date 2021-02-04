package io.github.fobo66.factcheckerassistant.ui.guide

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val GUIDE_TEXT_PADDING = 16
const val GUIDE_TITLE_SIZE = 18

private val guideTextPadding = GUIDE_TEXT_PADDING.dp
private val guideTitleSize = GUIDE_TITLE_SIZE.sp

@Composable
fun SectionTitle(@StringRes stringRes: Int) {
    Text(
        text = stringResource(id = stringRes),
        fontWeight = FontWeight.Bold,
        fontSize = guideTitleSize,
        modifier = Modifier.padding(guideTextPadding)
    )
}

@Composable
fun GuideParagraph(@StringRes stringRes: Int) {
    Text(
        text = stringResource(id = stringRes),
        modifier = Modifier.padding(guideTextPadding)
    )
}


@Composable
fun Preamble(@StringRes stringRes: Int) {
    Text(
        text = stringResource(id = stringRes),
        fontStyle = FontStyle.Italic,
        modifier = Modifier.padding(guideTextPadding)
    )
}
