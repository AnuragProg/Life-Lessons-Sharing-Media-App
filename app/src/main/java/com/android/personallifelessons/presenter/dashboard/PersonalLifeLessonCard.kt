@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.android.personallifelessons.presenter.dashboard

import android.icu.util.Calendar
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.android.personallifelessons.presenter.components.DashboardIconActions
import com.android.personallifelessons.presenter.components.TimestampConvertor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonalLifeLessonCard(
    modifier: Modifier = Modifier,
    dividerThickness: Dp = 3.dp,
    pll: PersonalLifeLesson, userId: String,
    action: ((DashboardIconActions) -> Unit)? = null
) {

    var showMore by remember { mutableStateOf(false) }
    var showShowMore by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = pll.username, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(text = TimestampConvertor.timeThenDate(pll.createdOn, " | "),
                fontWeight = FontWeight.Light, fontSize=10.sp)
            Divider(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                thickness = dividerThickness
            )

            Text(text = pll.title, fontWeight = FontWeight.Medium, fontSize = 20.sp)
            Spacer(Modifier.height(5.dp))
            Text(
                modifier = Modifier.animateContentSize(animationSpec = tween()),
                text = pll.relatedStory,
                fontSize = 14.sp,
                maxLines = if (showMore) Int.MAX_VALUE else 5,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    if(!showMore){ showShowMore = textLayoutResult.hasVisualOverflow }
                }
            )
            if (showShowMore) {
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .align(Alignment.End)
                        .combinedClickable(
                            onClick = {
                                showMore = !showMore
                            }
                        ),
                    text = if (showMore) "Show Less" else "Show More",
                    color = Color.Blue
                )
            }
            if(action!=null){
                Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                    thickness = dividerThickness)
                PersonalLifeLessonBottomBar(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    pll = pll,
                    showDeleteButton = pll.userId != userId,
                    action = action
                )
            }
        }
    }
}

@Composable
fun PersonalLifeLessonBottomBar(
    modifier: Modifier = Modifier,
    pll: PersonalLifeLesson,
    showDeleteButton: Boolean = false,
    action: (DashboardIconActions) -> Unit

) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column {
                Icon(
                    modifier = Modifier.clickable {
                        action(DashboardIconActions.LIKE)
                    },
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Like"
                )
            }
            BadgedBox(
                badge = {
                    Badge(content = { Text(text = pll.comments.size.toString()) })
                }
            ) {
                Column {
                    Icon(
                        modifier = Modifier.clickable {
                            action(DashboardIconActions.COMMENT)
                        },
                        imageVector = Icons.Filled.Comment,
                        contentDescription = "Comment"
                    )
                }
            }
            Column {
                Icon(
                    modifier = Modifier.clickable {
                        action(DashboardIconActions.SHARE)
                    },
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share"
                )
            }
            Column {
                Icon(
                    modifier = Modifier.clickable {
                        action(DashboardIconActions.SAVE)
                    },
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Share"
                )
            }
            if (showDeleteButton) {
                Column {
                    Icon(
                        modifier = Modifier.clickable {
                            action(DashboardIconActions.DELETE)
                        },
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PersonalLifeLessonCardPreview() {
    val p = PersonalLifeLesson(
        "something", ";laksj;",
        "Anurag singh", "How I survived tutorial hell?",
        "Build projects to learn original development",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris pharetra sapien in lectus imperdiet, vitae interdum ante volutpat. Donec egestas eget metus vel congue. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Phasellus eget cursus turpis. Nullam sed erat vehicula, malesuada diam aliquet, egestas metus. Vestibulum eleifend lobortis libero ut gravida. Ut consequat, diam non dictum pellentesque, enim ligula efficitur felis, ut suscipit ante velit varius diam. Pellentesque consequat, massa quis dignissim ornare, nisl libero ultricies orci, eget efficitur sapien est vitae nisi. Nam pulvinar, lorem in finibus aliquet, erat nisl imperdiet eros, eget auctor lectus dui quis tortor. Vivamus suscipit leo et ipsum maximus, eu tempus ligula pellentesque.\n" +
                "\n" +
                "Phasellus pulvinar erat sed placerat facilisis. Duis sed elit ac metus egestas accumsan sed et dui. Praesent imperdiet nec felis in elementum. Nullam feugiat et mauris non interdum. Morbi ipsum tortor, euismod nec lorem a, cursus rutrum nunc. Aenean et malesuada velit. Nullam sapien ante, gravida id diam eu, lobortis aliquet diam. Suspendisse lacinia urna purus, et hendrerit ipsum lacinia at. Pellentesque mollis massa nec urna pretium, eget tristique elit pretium. Suspendisse semper sit amet arcu in euismod. Donec vitae nunc convallis, iaculis risus vitae, viverra libero.",
        Calendar.getInstance().time.time,
        "Career Advice"
    )
    PersonalLifeLessonCard(
        modifier = Modifier.padding(10.dp),
        pll = p,
        userId = "",
        action = {}
    )
}