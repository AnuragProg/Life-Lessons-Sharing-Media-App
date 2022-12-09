package com.android.personallifelessons.presenter.navgraph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.personallifelessons.R
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.presenter.category.CategoryScreen
import com.android.personallifelessons.presenter.comments.CommentScreen
import com.android.personallifelessons.presenter.components.Destinations
import com.android.personallifelessons.presenter.components.decodeToT
import com.android.personallifelessons.presenter.components.encodeToParcel
import com.android.personallifelessons.presenter.dashboard.DashboardScreen
import com.android.personallifelessons.presenter.postAndUpdate.PostAndUpdatePllScreen
import com.android.personallifelessons.presenter.shared.CustomErrorMessagePage
import kotlinx.coroutines.flow.collectLatest


@Composable
fun Index(){
    val navController = rememberNavController()

    var currentDestination by remember{mutableStateOf(Destinations.DASHBOARD)}

    // Custom navigate function to keep track of current destination
    fun navigate(route: Destinations, arg: String? = null){
        arg?.let{
            val routeToNavigate = route.route+"/$arg"
            navController.navigate(routeToNavigate){
                launchSingleTop = true
            }
            return
        }
        val routeToNavigate = route.route
        navController.navigate(routeToNavigate){
            launchSingleTop = true
        }
    }


    LaunchedEffect(Unit){
        navController.currentBackStackEntryFlow.collectLatest{
            it.destination.route?.let{ route ->
                currentDestination = Destinations.findDestination(route)
            }
        }
    }


    Scaffold(
        topBar = {
                 AppBar(title = stringResource(id = R.string.app_name))
        },
        bottomBar = {
            if(currentDestination != Destinations.POSTANDUPDATE)
                BottomNav(currentDest = currentDestination){ dest ->
                    navigate(route = dest)
                }
        }
    ) { padding ->
        NavigationGraph(padding = padding, navController = navController, navigate = ::navigate)
    }

}



@Composable
fun NavigationGraph(
    padding: PaddingValues,
    navController: NavHostController,
    navigate: (route:Destinations, arg:String?) -> Unit,
){
    NavHost(modifier= Modifier.padding(padding),navController = navController, startDestination = Destinations.DASHBOARD.route){


        composable(Destinations.DASHBOARD.route){

            DashboardScreen{ dest: Destinations,  pll : Pll?->
                // On Personal life lesson post click
                if(dest == Destinations.COMMENT && pll!=null){
                    navigate(Destinations.COMMENT, pll.encodeToParcel())
                }else
                    navigate(dest, null)
            }
        }
        
        composable(Destinations.CATEGORY.route){
            CategoryScreen()
        }
        
        composable(
            route = Destinations.COMMENT.route+"/{pll}",
            arguments = listOf(
                navArgument("pll"){ type = NavType.StringType }
            )
        ){ backStackEntry ->
            var pll : Pll? = null
            try{
                pll = backStackEntry.arguments!!.getString("pll")!!.decodeToT<Pll>()
            }catch(_: Exception){}

            if(pll!=null)
                CommentScreen(initialPll = pll)
            else
                CustomErrorMessagePage(msg = "Data transportation error")
        }

        composable(
            route=Destinations.POSTANDUPDATE.route+"?pll={pll}",
            arguments = listOf(
                navArgument("pll"){
                    nullable = true
                    type = NavType.StringType
                }
            )
        ){
            val pll = it.arguments?.getString("pll")?.decodeToT<Pll>()
            PostAndUpdatePllScreen(pll){ dest ->
                navigate(dest,null)
            }
        }
    }
}
