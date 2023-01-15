package com.android.personallifelessons.presenter.dashboard

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.components.ApiException
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.components.ServerConnectionError
import com.android.personallifelessons.components.sharePll
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.presenter.components.Destinations
import com.android.personallifelessons.presenter.shared.*
import es.dmoral.toasty.Toasty
import org.koin.androidx.compose.koinViewModel
import java.util.*


@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    moveToAuthActivity: ()->Unit,
    onNavigate: (Destinations, Pll?)->Unit,
) {
    val context = LocalContext.current
    var state by remember { mutableStateOf<Outcome<String>>(Outcome.Loading) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var pllId by remember { mutableStateOf<String?>(null) }
    val plls by viewModel.plls.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Log.d("viewmodel", "DashboardScreen - viewmodel - $viewModel")
    }

    if (showDeleteConfirmation) {
        DeleteConfirmationDialog(
            onConfirm = { viewModel.deletePost(pllId!!) },
            onReject = {},
            hide = {
                showDeleteConfirmation = false
                pllId = null
            }
        )
    }

    LaunchedEffect(uiState) {
        state = uiState
    }


    when (state) {
        is Outcome.Error -> {
            if ((state as Outcome.Error).error is ServerConnectionError)
                ServerErrorPage()
            else if ((state as Outcome.Error).error is ApiException) {
                if ((state as Outcome.Error).error.code == 401)
                    moveToAuthActivity()
            } else NoDataErrorPage()
            Toasty.error(context, (state as Outcome.Error).error.message).show()
        }
        Outcome.Loading -> {
            LoadingPage()
        }
        is Outcome.Success -> {
            if (plls.isEmpty()) {
                NoDataErrorPage()
            } else {
                Box {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(plls) { pll ->
                            PllCard(
                                pll = pll,
                                isPostLiked = { viewModel.isPostLiked(pll) },
                                isPostInCache = { viewModel.isPostInCache(pll) },
                                onClick = {
                                    onNavigate(Destinations.COMMENT, it)
                                },
                                liked = {
                                    viewModel.likePost(pll)
                                },
                                disliked = {
                                    viewModel.dislikePost(pll)
                                },
                                onDeleteClick = {
                                    pllId = pll._id
                                    showDeleteConfirmation = true
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
                }
            }
        }
    }
}
