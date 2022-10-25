package com.android.personallifelessons.presenter.post

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.components.Outcome
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun PostScreen() {

    val viewModel = getViewModel<PostViewModel> { parametersOf("userId", "username") }

    val title by viewModel.title.collectAsStateWithLifecycle()
    val learning by viewModel.learning.collectAsStateWithLifecycle()
    val relatedStory by viewModel.relatedStory.collectAsStateWithLifecycle()
    val category by viewModel.category.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(uiState){
        when (uiState) {
            is Outcome.Error -> {
                Toast.makeText(context,
                    (uiState as Outcome.Error<String>).message!!,
                    Toast.LENGTH_SHORT).show()
            }
            is Outcome.Loading -> {}
            is Outcome.Success -> Toast.makeText(context, uiState!!.data, Toast.LENGTH_SHORT).show()
            null -> {}
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = category?.nam ?: "Select Category",
                    onValueChange = {
                        viewModel.setCategory(Category.extractCategoryFromNam(it))
                    },
                    readOnly = true
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    Category.values().forEach { category ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                viewModel.setCategory(Category.extractCategoryFromNam(category.nam))
                            })
                        {
                            Text(category.nam)
                        }
                    }
                }
            }
            //Title
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = viewModel::setTitle,
                label = { Text("Title") }
            )
            Spacer(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            //Learning
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = learning,
                onValueChange = viewModel::setLearning,
                label = { Text("Learning") }
            )
            Spacer(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            //RelatedStory
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true),
                value = relatedStory,
                onValueChange = viewModel::setRelatedStory,
                label = { Text("Related Story") }
            )
        }
        Box(modifier = Modifier
            .padding(30.dp)
            .fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(onClick = {
                viewModel.postPersonalLifeLesson()
            }) {
                Icon(imageVector = Icons.Filled.PostAdd, contentDescription = "Save")
            }
        }
    }
}

@Preview
@Composable
fun PostScreenPreview() {
    PostScreen()
}