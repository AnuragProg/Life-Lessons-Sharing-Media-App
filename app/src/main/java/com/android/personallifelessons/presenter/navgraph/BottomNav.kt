package com.android.personallifelessons.presenter.navgraph

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.personallifelessons.presenter.components.Destinations


@Composable
fun BottomNav(
    currentDest: Destinations = Destinations.DASHBOARD,
    onNavigate: (Destinations)->Unit
) {
    BottomNavigation{
        for(dest in Destinations.values().filter{it.onBottomNav}){
            BottomNavigationItem(
                selected = dest == currentDest,
                onClick = { onNavigate(dest) },
                icon = {
                    Icon(dest.icon!!, null)
                },
                label = {
                    Text(text=dest.label)
                }
            )
        }
    }
}



@Preview
@Composable
fun BottomNavPreview() {
    BottomNav(){}
}
