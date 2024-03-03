package com.example.githubusernavigationdanapi.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubusernavigationdanapi.ui.theme.GithubusernavigationdanapiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    modifier: Modifier = Modifier,
    query: String,
    onSearch: (String) -> Unit,
    onQuery: (String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToFavorite: () -> Unit,
    navigateToAboutme:() -> Unit,
    ) {
    SearchBar(
        query = query,
        onQueryChange = onQuery,
        onSearch = onSearch,
        active = false,
        onActiveChange = {},
        leadingIcon = {
              Icon(
                imageVector = Icons.Filled.Search,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.onSurfaceVariant
              )
        },
        trailingIcon = {
            Row(
                modifier = modifier
                    .padding(horizontal = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .clickable {
                            navigateToFavorite()
                        }
                )
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = modifier
                        .padding(horizontal = 5.dp)
                        .clickable {
                            navigateToAboutme()
                        }
                )

                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = modifier
                        .padding(horizontal = 5.dp)
                        .clickable {
                            navigateToSettings()
                        }
                )
            }
        },
        placeholder = {
                      Text("Cari User")
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ){}
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    GithubusernavigationdanapiTheme {
        Search(
            query = "",
            onSearch = {},
            onQuery = {},
            navigateToFavorite = {},
            navigateToSettings = {},
            navigateToAboutme = {}
        )
    }
}