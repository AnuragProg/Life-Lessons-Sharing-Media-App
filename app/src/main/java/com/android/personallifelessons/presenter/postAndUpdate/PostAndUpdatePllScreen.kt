package com.android.personallifelessons.presenter.postAndUpdate

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.PllRequest
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.presenter.components.Destinations
import es.dmoral.toasty.Toasty
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class)
@Composable
fun PostAndUpdatePllScreen(
    pll: Pll? = null,
    viewModel: PostAndUpdatePllViewModel = koinViewModel{parametersOf(pll)},
    onNavigate:(Destinations)->Unit,
) {
    // To show to user
    val title by viewModel.title.collectAsStateWithLifecycle()
    val learning by viewModel.learning.collectAsStateWithLifecycle()
    val relatedStory by viewModel.relatedStory.collectAsStateWithLifecycle()
    val category by viewModel.category.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var loading by remember{ mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Keep track of uiState
    LaunchedEffect(uiState){
        when(uiState){
            is Outcome.Error -> {
                loading = false
                Toasty.error(context, (uiState as Outcome.Error).error.message!!).show()
            }
            Outcome.Loading ->{
                loading = true
            }
            is Outcome.Success -> {
                loading = false
                keyboardController?.hide()
                onNavigate(Destinations.DASHBOARD)
                Toasty.success(context, (uiState as Outcome.Success).data).show()
            }
            null -> {}
        }
    }

    Box{

        // Input fields
        Column(
            modifier=Modifier.fillMaxSize()
        ){
            var isExpanded by remember{mutableStateOf(false)}
            ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {isExpanded=!isExpanded}) {
                OutlinedTextField(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    value = category?.title ?:"Select Category", onValueChange = viewModel::setTitle,
                    label = {
                        Text("Category")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    readOnly = true
                )
                
                ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false}) {
                    categories.forEach{ cat ->
                        DropdownMenuItem(modifier = Modifier.fillMaxWidth(),onClick = { viewModel.setCategory(cat); isExpanded = false}) {
                            Text(text=cat.title)
                        }
                    }
                }
            }

            OutlinedTextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                value = title, onValueChange = viewModel::setTitle,
                label = {
                    Text("Title")
                }
            )
            OutlinedTextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                value = learning, onValueChange = viewModel::setLearning,
                label = {
                    Text("Learning")
                }
            )
            OutlinedTextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .weight(1f),
                value = relatedStory, onValueChange = viewModel::setRelatedStory,
                label = {
                    Text("Related Story")
                }
            )
        }

        // To post or update the personal life lesson
        Box(Modifier
            .padding(10.dp)
            .fillMaxSize(), contentAlignment = Alignment.BottomEnd){
            FloatingActionButton(onClick = viewModel::postOrUpdate) {
                Icon(Icons.Filled.PostAdd, null)
            }
        }

        // For showing loading state
        if(loading){
            Box(Modifier.fillMaxSize(), contentAlignment=Alignment.Center){
                CircularProgressIndicator()
            }
        }
    }
}


@Preview
@Composable
fun PostAndUpdatePllScreenPreview() {
    val re = PllRequest(
        "",
        "",
    "",
        ""
    )
    PostAndUpdatePllScreen(onNavigate = {})
}

