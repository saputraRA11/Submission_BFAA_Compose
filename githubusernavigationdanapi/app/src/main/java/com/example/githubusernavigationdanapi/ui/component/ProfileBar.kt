package com.example.githubusernavigationdanapi.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubusernavigationdanapi.ui.screen.detail.DetailItem
import com.example.githubusernavigationdanapi.ui.theme.GithubusernavigationdanapiTheme

@Composable
fun ProfileBar(
    modifier: Modifier = Modifier,
    user:DetailItem
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(bottom = 20.dp),
    ) {
        AsyncImage(
            model = user.photoUrl ?: "",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 8.dp)
                .size(100.dp)
                .clip(CircleShape)
        )

        Text(
            text = user.name ?: "",
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(top=20.dp)
        )

        Text(
            text = user.username ?: "",
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(top = 10.dp)
        )

        Row (
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(top = 5.dp,start = 5.dp)
                .fillMaxWidth()
        ){

            Text(
                text = "Work At",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
            )

            Text(
                text = user.work ?: "",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }

        Row (
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(top = 5.dp,start = 5.dp)
                .fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "${user.location ?: ""}",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }

        Row (
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(top = 5.dp, start = 5.dp)
                .fillMaxWidth()
        ){

            Icon(
                imageVector = Icons.Filled.Face,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Followings",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 5.dp)
            )

            Text(
                text = "${user.followings ?: 0}",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 5.dp)
            )

            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(start = 20.dp)
            )

            Text(
                text = "Followers",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 5.dp)
            )

            Text(
                text = "${user.followers ?: 0}",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ProfileBarPreview() {
    GithubusernavigationdanapiTheme {
        val user = DetailItem(
            work = "Bali",
            followings = 10,
            followers = 20,
            name = "Saputra Ari Wijaya",
            username = "Saputra@@@",
            photoUrl = "http",
            location = "",
            id = 10
        )

        ProfileBar(
         user = user
        )
    }
}
