package io.github.zyrouge.symphony.ui.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ActionButton(
    content: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    squish: Boolean = true,
) {
    TextButton(onClick = onClick, enabled = enabled) {
        Text(
            content,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.let { if (squish) it.width(IntrinsicSize.Min) else it },
        )
    }
}