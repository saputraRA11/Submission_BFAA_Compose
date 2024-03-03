package com.example.githubusernavigationdanapi.ui.component

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubusernavigationdanapi.ui.theme.GithubusernavigationdanapiTheme

@Composable
fun SwithMode(
    checked:Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier:Modifier = Modifier
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}

@Preview(
    showBackground = true
)
@Composable
fun SwithModePreview(
    modifier:Modifier = Modifier
) {
    GithubusernavigationdanapiTheme {
        Switch(
            checked = false,
            onCheckedChange = {}
        )
    }
}

