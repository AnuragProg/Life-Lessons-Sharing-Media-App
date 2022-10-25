package com.android.personallifelessons.presenter.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.personallifelessons.domain.model.Category

@Composable
fun CategoryColumn(
    modifier: Modifier = Modifier,
    categories: List<Category>,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
    ) {
        for (category in categories) {
            CategoryCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                category = category
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
) {
    var showMore by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        onClick = { showMore = !showMore }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = category.categoryName,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.categoryDescription,
                maxLines = 10,
                fontSize = 10.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun CategoryCardPreview() {

    val category = Category("2lkj3klj3l4",
        "Career Advice",
        "This is the tip that comes along from a colleague or friend about what your next career move should be. The danger here is that the advice is based primarily if not solely on information you have relayed to the advice-giver, which might, in itself, determine the advice coming back.\n")

    CategoryCard(
        modifier = Modifier.padding(10.dp),
        category = category
    )

}

@Preview
@Composable
fun CategoryColumnPreview() {

    val category = Category("2lkj3klj3l4",
        "Career Advice",
        "This is the tip that comes along from a colleague or friend about what your next career move should be. The danger here is that the advice is based primarily if not solely on information you have relayed to the advice-giver, which might, in itself, determine the advice coming back.\n")

    CategoryColumn(
        modifier = Modifier.fillMaxSize(),
        categories = listOf(category)
    )

}