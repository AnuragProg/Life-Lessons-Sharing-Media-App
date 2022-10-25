package com.android.personallifelessons.presenter.comments

import android.icu.util.Calendar
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.personallifelessons.domain.model.Comment
import com.android.personallifelessons.presenter.components.TimestampConvertor

@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    comment: Comment
) {

    Card(
        modifier = modifier
    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = comment.username,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Text(
                    text = TimestampConvertor.longToFormattedTime(comment.commentedOn),
                    fontSize = 10.sp
                )
            }
            Spacer(modifier=Modifier.height(5.dp))
            Text(
                text = comment.comment,
                fontSize = 18.sp
            )
        }
    }

}

@Preview
@Composable
fun CommentCardPreview() {
    CommentCard(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        comment = Comment("jkasd;jk",";alksjdf","Anurag Singh", "Its amazing!", Calendar.getInstance().time.time)
    )
}