package com.nocountry.listmate.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.nocountry.listmate.R
import com.nocountry.listmate.ui.theme.ListMateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    title: Int,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)?
) {
    TopAppBar(title = {
        Text(
            text = stringResource(id = title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    },
        modifier = modifier,
        navigationIcon = navigationIcon ?: {})

}

@Preview
@Composable
fun TopBarComponentPreview() {
    ListMateTheme {
        TopBarComponent(title = R.string.app_name, navigationIcon = {})
    }
}