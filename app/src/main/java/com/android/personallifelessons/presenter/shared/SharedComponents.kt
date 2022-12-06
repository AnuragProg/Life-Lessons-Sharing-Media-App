@file:OptIn(ExperimentalMaterialApi::class)

package com.android.personallifelessons.presenter.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.android.personallifelessons.R
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.presenter.components.TimestampConvertor.dateThenTime


/* For Update/Delete operations */
@Composable
fun OverflowMenu(){
    var expanded by remember{mutableStateOf(true)}
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false}) {
        DropdownMenuItem(onClick = { /*TODO*/ }) {
            Text("Edit")
        }
        DropdownMenuItem(onClick = { /*TODO*/ }) {
            Text("Delete")
        }
    }
}


/* Composable for Personal Life Lesson Card */
@Composable
fun PllCard(
    pll: Pll,
    isOwner: ()->Boolean = {false},
    isLiked: ()->Boolean = {false},
    liked: (()->Unit)? = null,
    disliked: (()->Unit)? = null,
    onClick: ((Pll)->Unit)? = null
) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            if (onClick != null) {
                onClick(pll)
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(modifier=Modifier.size(50.dp), painter = painterResource(id = R.drawable.user), contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    // username
                    Text(
                        text=pll.username, fontWeight= FontWeight.Bold, fontSize = 20.sp
                    )
                }
                Text(
                    text=dateThenTime(pll.createdOn, "\n"), fontWeight=FontWeight.Light, fontSize=10.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            // title
            Text(
                text="Title",fontWeight=FontWeight.SemiBold, fontSize=18.sp
            )
            Text(
                text=pll.title, fontWeight=FontWeight.Normal, fontSize=16.sp
            )

            Spacer(Modifier.height(8.dp))

            // Learning
            Text(
                text="Learning", fontWeight=FontWeight.SemiBold, fontSize=18.sp
            )
            Text(
                text=pll.learning, fontWeight=FontWeight.Normal, fontSize=16.sp
            )


            Spacer(Modifier.height(8.dp))

            // Content
            Text(
                text="Related Story", fontWeight=FontWeight.SemiBold, fontSize=18.sp
            )
            Text(
                text=pll.relatedStory, fontWeight=FontWeight.Normal, fontSize=16.sp
            )

            Spacer(Modifier.height(8.dp))

            Divider(modifier=Modifier.fillMaxWidth())

            // Bottom Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){

                // For toggling like button
                var likedIndicator by remember{mutableStateOf(isLiked())}
                IconButton(onClick = {
                    likedIndicator = if(likedIndicator) {
                        if(disliked!=null)disliked()
                        false
                    }else{
                        if(liked!=null) liked()
                        true
                    }
                }) {
                    if(likedIndicator) Icon(Icons.Outlined.Favorite, null, tint= Color.Red)
                    else Icon(Icons.Outlined.FavoriteBorder, null, tint= Color.Gray)
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Comment, null, tint=Color.Gray)
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Share, null, tint=Color.Gray)
                }
                if(isOwner()){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Delete, null, tint=Color.Gray)
                    }
                }
            }
        }
    }
}


@Preview(showBackground=true, showSystemUi=true)
@Composable
fun PllCardPreview(){
//    PllCard(pll)
}

@Composable
fun ErrorPage(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.error_no_data))
        val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)
        LottieAnimation(
            composition = composition, progress = { progress }
        )
    }
}

@Composable
fun CustomErrorMessagePage(msg: String){
    Box{
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.bot_error_404))
            val progress by animateLottieCompositionAsState(composition = composition)
            LottieAnimation(
                composition = composition, progress = { progress }
            )
            Spacer(Modifier.height(10.dp))
            Text(text=msg, fontWeight=FontWeight.SemiBold, fontSize=20.sp)
        }
    }
}

@Composable
fun LoadingPage(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator()
    }
}


@Composable
fun DeleteConfirmationDialog(
    onConfirm: ()->Unit,
    onReject: ()->Unit,
){
    var visible by remember{mutableStateOf(true)}
    AlertDialog(
        onDismissRequest = { visible = false},
        confirmButton = {
            Button(onClick = {onConfirm(); visible=false}){
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = {onReject(); visible=false}){
                Text("Cancel")
            }
        }
    )
}