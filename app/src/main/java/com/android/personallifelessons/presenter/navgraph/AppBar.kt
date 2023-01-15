package com.android.personallifelessons.presenter.navgraph

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.personallifelessons.R
import com.android.personallifelessons.presenter.components.Destinations


@Composable
fun AppBar(
    title: String,
    signOut: () -> Unit,
    currentDestination: Destinations,
    onNavigate: (Destinations) -> Unit
) {
    var showLogoDropdown by remember{ mutableStateOf(false) }
    TopAppBar(
        title ={
            Text(text=title)
        },
        actions = {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(10.dp)
                    .clickable(enabled = true, role = Role.Image) {
                        showLogoDropdown = true
                    },
                painter = painterResource(id = R.drawable.user),
                contentDescription = null
            )

            // Logo click dropdown menu
            DropdownMenu(
                expanded = showLogoDropdown,
                onDismissRequest = {showLogoDropdown=false})
            {

                //Sign out button
                DropdownMenuItem(
                    onClick = signOut
                ) {
                    Text("Sign out")
                }
            }
        },
        navigationIcon = if(currentDestination in setOf(Destinations.POSTANDUPDATE, Destinations.COMMENT)){
            {
                IconButton(
                    modifier = Modifier.clip(CircleShape),
                    onClick = {onNavigate(Destinations.DASHBOARD)}
                ){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        }else null
    )

}


@Preview
@Composable
fun AppBarPreview() {
    AppBar(stringResource(R.string.app_name), {}, Destinations.DASHBOARD, {})
}