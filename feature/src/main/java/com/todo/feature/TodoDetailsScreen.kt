package com.todo.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun TodoDetailsScreen(
    navController: NavHostController,
    viewModel: TodoViewModel = hiltViewModel()
) {

    TodoDetailsScreenContent(
        onAddTodoButtonClick = {
            viewModel.addTodo(onComplete = {
                navController.popBackStack()
            })
        },
        todo = { viewModel.todo.value },
        onTodoValueChange = { viewModel.onTodoValueChange(it) },
        addTodoInProgress = { viewModel.addTodoInProgress.value },
        isError = { viewModel.isTodoError.value })

}

@Composable
private fun TodoDetailsScreenContent(
    onAddTodoButtonClick: () -> Unit,
    todo: () -> String,
    onTodoValueChange: (String) -> Unit,
    addTodoInProgress: () -> Boolean,
    isError: () -> Boolean
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = todo(),
                onValueChange = { onTodoValueChange(it) },
                label = { Text("TODO Item") },
                singleLine = true,
                isError = isError(),
                enabled = addTodoInProgress().not()
            )

            if (isError()) {
                Text(
                    text = "Error message",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                )
            }

            Button(
                onClick = {
                    onAddTodoButtonClick()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp),
                enabled = addTodoInProgress().not()
            ) {
                Text("Add TODO")
            }
//        if (/*errorMessage.isNotEmpty()*/ true) {
//            Text("errorMessage", color = Color.Red, modifier = Modifier.padding(top = 16.dp))
//        }
        }

        if (addTodoInProgress()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}

@Composable
@Preview
private fun TodoDetailsScreenContentPreview() {
    TodoDetailsScreenContent(
        onAddTodoButtonClick = {},
        todo = { "" },
        onTodoValueChange = {},
        addTodoInProgress = { false }, isError = { true })
}