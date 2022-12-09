package com.android.personallifelessons.presenter.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.components.ServerConnectionError
import com.android.personallifelessons.data.dto.response.CategoryResponse
import com.android.personallifelessons.presenter.shared.NoDataErrorPage
import com.android.personallifelessons.presenter.shared.LoadingPage
import com.android.personallifelessons.presenter.shared.ServerErrorPage
import es.dmoral.toasty.Toasty
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel()
){

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        is Outcome.Success -> {
            Box{
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(state.data){ category ->
                        CategoryCard(category = category)
                    }
                }
            }
        }
    }
}



@Composable
fun CategoryCard(
    category:CategoryResponse
){
    Card(
        modifier=Modifier.padding(10.dp),
        shape = RoundedCornerShape(10.dp)
    ){
        Column(
            modifier= Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            Text(
                text="Type", fontWeight = FontWeight.Bold, fontSize=20.sp
            )
            Text(
                text=category.title, fontWeight= FontWeight.Normal, fontSize=16.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text="Description", fontWeight = FontWeight.Bold, fontSize=20.sp
            )
            Text(
                text=category.description, fontWeight=FontWeight.Normal, fontSize=16.sp
            )
        }
    }
}

@Preview
@Composable
fun CategoryCardPreview(){
    val category = CategoryResponse(
        "", "Solicited Advice", "An advice givent to something and something"
    )
    CategoryCard(category)
}
