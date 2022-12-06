package com.android.personallifelessons.presenter.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.data.dto.response.CommentResponse
import com.android.personallifelessons.presenter.components.TimestampConvertor.dateThenTime
import com.android.personallifelessons.R
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.data.dto.response.PllResponse
import com.android.personallifelessons.presenter.shared.ErrorPage
import com.android.personallifelessons.presenter.shared.LoadingPage
import com.android.personallifelessons.presenter.shared.PllCard
import es.dmoral.toasty.Toasty
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.*


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CommentScreen(
    pll: Pll,
    viewModel: CommentViewModel = koinViewModel{ parametersOf(pll) }
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(val state = uiState){
        is Outcome.Error -> {
            Toasty.error(context, state.error.message!!).show()
            ErrorPage()
        }
        Outcome.Loading -> {
            LoadingPage()
        }
        is Outcome.Success ->{
            Box{
                Column(Modifier.fillMaxSize()){
                    PllCard(pll = pll)
                    LazyColumn(modifier=Modifier.fillMaxSize()){
                        items(state.data){ comment ->
                            CommentCard(comment = comment)
                        }
                    }
                }
            }
        }
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
                    text=dateThenTime(comment.commentedOn, "\n"), fontWeight=FontWeight.Light, fontSize=10.sp
                )
            }
            Spacer(Modifier.height(8.dp))

            Text(
                text=comment.comment, fontWeight=FontWeight.Normal, fontSize = 16.sp
            )
        }
    }
}

@Preview()
@Composable
fun CommentCardPreview(){
    val comment = CommentResponse(
        "","","", "Anurag Singh", "It's amazing lesson to everyone", Calendar.getInstance().time.time
    )
    CommentCard(comment = comment)
}




