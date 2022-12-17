package com.android.personallifelessons.presenter.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.components.ServerConnectionError
import com.android.personallifelessons.components.sharePll
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.presenter.components.Destinations
import com.android.personallifelessons.presenter.shared.*
import es.dmoral.toasty.Toasty
import org.koin.androidx.compose.koinViewModel
import java.util.*


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    onNavigate: (Destinations, Pll?)->Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteConfirmation by remember{mutableStateOf(false)}
    var pllId by remember{mutableStateOf<String?>(null)}

    if(showDeleteConfirmation){
        DeleteConfirmationDialog(
            onConfirm = { viewModel.deletePost(pllId!!) },
            onReject = {},
            hide = {
              showDeleteConfirmation = false
                pllId = null
            }
        )
    }




    when(val state = uiState){
        is Outcome.Error -> {
            if(state.error is ServerConnectionError)
                ServerErrorPage()
            else NoDataErrorPage()
            Toasty.error(context, state.error.message!!).show()
        }
        Outcome.Loading -> {
            LoadingPage()
        }
        is Outcome.Success ->{
            Box{
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    items(viewModel.plls.value){ pll ->
                        PllCard(
                            pll = pll,
                            onClick = {
                                onNavigate(Destinations.COMMENT, it)
                            },
                            isLiked = { viewModel.isLiked(pll) },
                            liked = {
                                    viewModel.likePost(pll)
                            },
                            disliked = {
                                viewModel.dislikePost(pll)
                            },
                            onDeleteClick = {
                                pllId = pll._id
                                showDeleteConfirmation=true
                            },
                            onCommentClick = {
                                onNavigate(Destinations.COMMENT, pll)
                            },
                            onShareClick = {
                                context.sharePll(pll)
                            }
                        )
                    }
                }
                Box(Modifier
                    .fillMaxSize()
                    .padding(end = 10.dp, bottom = 10.dp), contentAlignment = Alignment.BottomEnd){
                    FloatingActionButton(onClick = { onNavigate(Destinations.POSTANDUPDATE, null) }) {
                        Icon(Icons.Filled.PostAdd, null)
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen() { _, _ -> }
}