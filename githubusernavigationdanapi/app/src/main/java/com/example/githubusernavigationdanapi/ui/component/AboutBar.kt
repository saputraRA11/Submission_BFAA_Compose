package com.example.githubusernavigationdanapi.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubusernavigationdanapi.data.model.MyProfile

@Composable
fun AboutBar(
    profile:MyProfile,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(bottom = 20.dp),
    ) {
        AsyncImage(
            model = profile.photoUrl ?: "",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(250.dp)
                .clip(CircleShape)
        )

        Text(
            text = profile.name ?: "",
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .padding(top=30.dp)
        )

        Text(
            text = profile.email ?: "",
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .padding(top = 10.dp)
        )
    }
}