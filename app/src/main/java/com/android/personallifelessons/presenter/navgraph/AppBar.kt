package com.android.personallifelessons.presenter.navgraph

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AppBar(
    title: String,

) {
    TopAppBar(
        title ={
            Text(text=title)
        }
    )

}

@Preview
@Composable
fun AppBarPreview() {
    
}