package com.android.personallifelessons.presenter.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.personallifelessons.domain.model.Comment
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.android.personallifelessons.presenter.dashboard.PersonalLifeLessonCard
import java.util.*

@Composable
fun CommentColumn(
    modifier : Modifier = Modifier,
    personalLifeLesson: PersonalLifeLesson,
    comments: List<Comment>
) {
    Column(
        modifier = modifier
    ){
        PersonalLifeLessonCard(
            pll = personalLifeLesson, userId = ""
        )
        Spacer(modifier=Modifier.height(5.dp))
        LazyColumn{
            items(comments) { comment ->
                CommentCard(
                    modifier = Modifier.padding(top=5.dp,bottom=5.dp),
                    comment = comment
                )
            }
        }
    }
}
@Preview
@Composable
fun CommentColumnPreview() {
    val personalLifeLesson = PersonalLifeLesson(
        "","","Username","How I survived Tutorial Hell?","Content",";laja;sdlkjf;alskjdfpoqwierpqoiwuerpoqwiuerpoqiuwerpoijdflkajljaslkdjflaskdfhajsdhfklahsdkfjhasdkjfhaskdjfhlaksjdfhlaksjdfh", Calendar.getInstance().time.time,
        ""
    )
    val comments = listOf(
        Comment(
            "commentId1",
        "user1",
    "username1",
    "It is amazing",
    3920930293
        ),
    Comment(
        "commentId2",
        "user2",
        "username2",
        "It is fresh",
        3920930293
    ),
    Comment(
        "commentId3",
        "user3",
        "username3",
        "It is mazing",
        3920930293
    )
    )
    CommentColumn(
        modifier=  Modifier.padding(10.dp),
        personalLifeLesson = personalLifeLesson,
        comments = comments
    )
}