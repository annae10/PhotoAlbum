package com.example.photoalbum.data.room

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserScreen (
    state: UserState,
    onEvent: (UserEvent) -> Unit
)
{
    Column {

    if(state.isAddingUser) {
        AddUserDialog(state = state, onEvent = onEvent)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "User profile",
            fontWeight = FontWeight.Bold

        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "First name",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = state.firstName
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Last name",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = state.lastName
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Email",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = state.email
        )
        Button(onClick = { onEvent(UserEvent.ShowDialog)}) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit user"
            )
        }
    }

    }

}