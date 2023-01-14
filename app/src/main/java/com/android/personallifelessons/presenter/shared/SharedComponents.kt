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
import com.android.personallifelessons.presenter.components.TimestampConvertor.stringToDate


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
    // Initial value of the post
    // First = isThisValueCachedOne
    // Second = isLiked
    isLiked: ()->Pair<Boolean, Boolean> = {Pair(false, false)},
    // User clicks on delete icon
    onDeleteClick: ()->Unit = {},
    // user clicks on like icon
    liked: (()->Unit)? = null,
    // user again clicks on like icon making it to dislike
    disliked: (()->Unit)? = null,
    // user clicks on card
    onClick: ((Pll)->Unit)? = null,
    // user clicks commentText icon
    onCommentClick : ()->Unit = {},
    // user clicks share icon
    onShareClick: ()->Unit = {},
    shouldShowDeleteButton: Boolean = true
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
                    text= stringToDate(pll.createdOn), fontWeight=FontWeight.Light, fontSize=10.sp
                )//dateThenTime(pll.createdOn, "\n")
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
                    likedIndicator = if(likedIndicator.second) {
                        if(disliked!=null)disliked()
                        Pair(true,false)
                    }else{
                        if(liked!=null) liked()
                        Pair(true,true)
                    }
                }) {
                    BadgedBox(badge = {
                        Badge{
                            Text("${ (pll.likes?.size ?: 0) + if(likedIndicator.first && likedIndicator.second) 1 else 0 }", color = Color.White)
                        }
                    }) {
                        if(likedIndicator.second) Icon(Icons.Outlined.Favorite, null, tint= Color.Red)
                        else Icon(Icons.Outlined.FavoriteBorder, null, tint= Color.Gray)
                    }
                }
                IconButton(onClick = onCommentClick) {
                    BadgedBox(badge = {
                        Badge{
                            Text("${pll.comments?.size ?: 0}", color = Color.White)
                        }
                    }) {
                        Icon(Icons.Outlined.Comment, null, tint=Color.Gray)
                    }
                }

                IconButton(onClick = onShareClick) {
                    Icon(Icons.Outlined.Share, null, tint=Color.Gray)
                }
                if(pll.isOwner && shouldShowDeleteButton){
                    IconButton(onClick = onDeleteClick) {
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
fun NoDataErrorPage(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.error_no_data))
        val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)
        LottieAnimation(
            composition = composition, progress = { progress }
        )
    }
}

@Composable
fun ServerErrorPage(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.bot_error_404))
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


/**
 * @param onConfirm Action to do when user confirms
 * @param onReject Action to do when user rejects
 * @param hide Clean up action to hide
 */
@Composable
fun DeleteConfirmationDialog(
    onConfirm: ()->Unit,
    onReject: ()->Unit,
    hide: ()->Unit
){
    AlertDialog(
        onDismissRequest = {
            hide()
            onReject()
                           },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                hide()
            }){
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onReject()
                hide()
            }){
                Text("Cancel")
            }
        },
        title = {
            Text("Delete Confirmation")
        },
        text = {
            Text("Do you want to delete this post?")
        }
    )
}