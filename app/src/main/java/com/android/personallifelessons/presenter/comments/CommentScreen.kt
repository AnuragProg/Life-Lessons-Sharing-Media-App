package com.android.personallifelessons.presenter.comments

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.R
import com.android.personallifelessons.components.ApiException
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.components.sharePll
import com.android.personallifelessons.data.dto.response.CommentResponse
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.presenter.components.TimestampConvertor
import com.android.personallifelessons.presenter.dashboard.DashboardViewModel
import com.android.personallifelessons.presenter.shared.LoadingPage
import com.android.personallifelessons.presenter.shared.NoDataErrorPage
import com.android.personallifelessons.presenter.shared.PllCard
import es.dmoral.toasty.Toasty
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

enum class State{SUCCESS, ERROR, LOADING}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommentScreen(
    initialPll: Pll,
    viewModel: CommentViewModel = koinViewModel{ parametersOf(initialPll) },
    dashboardViewModel: DashboardViewModel,
    moveToAuthActivity: ()->Unit
) {

    LaunchedEffect(Unit){
        Log.d("viewmodel", "Comment Screen - comment viewmodel - $viewModel")
        Log.d("viewmodel", "Comment Screen - dashboard viewmodel - $dashboardViewModel")
    }

    val context = LocalContext.current

    // for showing messages to user regarding any successful or unsuccessful operation
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Comments list to show to user
    val commentsList by viewModel.commentsList.collectAsStateWithLifecycle()

    // Comment text of the user
    val commentText by viewModel.commentText.collectAsStateWithLifecycle()

    // to hide keyboard on success/ failure
    val keyboardController = LocalSoftwareKeyboardController.current

    // to show appropriate screen to user
    // Can only be updated from within LaunchedEffect
    // hence preventing unnecessary recomposition
    var state by remember{mutableStateOf<State?>(null)}

    // Update pll so that when user comments
    // A new pll will be shown to user with updated badge
    val pll by viewModel.currentPll.collectAsStateWithLifecycle()

    LaunchedEffect(uiState){
        when(val ustate = uiState){
            is Outcome.Error -> {
                if(ustate.error is ApiException){
                    if(ustate.error.code == 401)
                        moveToAuthActivity()
                }
                Toasty.error(context, ustate.error.message!!).show()
            }
            Outcome.Loading -> {
                state = State.LOADING
            }
            is Outcome.Success ->{
                Toasty.success(context, ustate.data).show()
                state = State.SUCCESS
                keyboardController?.hide()
            }
        }
    }

    when(state){
        State.SUCCESS ->{
            Box{
                Column(Modifier.fillMaxSize()){
                    LazyColumn(modifier=Modifier.fillMaxSize()){
                        item {
                            PllCard(
                                pll = pll,
                                isPostLiked = {dashboardViewModel.isPostLiked(pll)},
                                isPostInCache ={ dashboardViewModel.isPostInCache(pll)},
                                liked = viewModel::likePost, disliked = viewModel::dislikePost,
                                onShareClick = {context.sharePll(pll)},
                                shouldShowDeleteButton = false
                            )
                            PostCommentCard(
                                text = commentText, onTextChange = viewModel::setComment,
                                onPostClicked = viewModel::postComment
                            )
                        }
                        items(commentsList){ comment ->
                            CommentCard(comment = comment)
                        }
                    }
                }
            }
        }
        State.ERROR ->{
            NoDataErrorPage()
        }
        State.LOADING -> {
            LoadingPage()
        }
        null -> {}
    }



}


@Composable
fun CommentCard(comment: CommentResponse){
    Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(10.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            // username Text(
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(modifier=Modifier.size(50.dp),painter = painterResource(id = R.drawable.user), contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text=comment.username, fontWeight= FontWeight.Bold, fontSize = 20.sp
                    )
                }
                Text(
                    text= TimestampConvertor.stringToDate(comment.commentedOn), fontWeight=FontWeight.Light, fontSize=10.sp
                )//dateThenTime(comment.commentedOn, "\n")
            }
            Spacer(Modifier.height(8.dp))

            Text(
                text=comment.comment, fontWeight=FontWeight.Normal, fontSize = 16.sp
            )
        }
    }
}


@Composable
fun PostCommentCard(
    text: String,
    onTextChange: (String)->Unit,
    onPostClicked: ()->Unit
){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        value = text, onValueChange = onTextChange,
        label = {
            Text("Comment")
        },
        trailingIcon = {
            IconButton(onClick = onPostClicked) {
                Icon(Icons.Filled.Send, null)
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}

@Preview(showBackground=true)
@Composable
fun PostCommentCardPreview() {
    var comment by remember{mutableStateOf("")}
    PostCommentCard(text = comment, onTextChange = {comment=it}) {

    }
}


@Preview()
@Composable
fun CommentCardPreview(){
//    val comment = CommentResponse(
//        "","","", "Anurag Singh", "It's amazing lesson to everyone", Calendar.getInstance().time.time
//    )
//    CommentCard(comment = comment)
}




