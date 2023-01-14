package com.android.personallifelessons.presenter.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.personallifelessons.R

@Composable
fun ProfileScreen() {

    var editUsername by remember{ mutableStateOf("") }
    var editPassword by remember{ mutableStateOf("") }

    val username by remember{mutableStateOf("Anurag Singh")}

    Box{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .padding(10.dp),
                painter = painterResource(id = R.drawable.user), contentDescription = null
            )
            Spacer(Modifier.height(20.dp))
            Text(text=username)
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = editUsername, onValueChange = {editUsername = it},
                label = {
                    Text("Edit username")
                }
            )
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = editPassword, onValueChange = {editPassword = it},
                label = {
                    Text("Edit password")
                }
            )
            Spacer(Modifier.height(20.dp))
            Button(onClick = { /*TODO*/ }) {
                Text("Save")
            }
        }
    }

}


@Preview(showBackground=true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
