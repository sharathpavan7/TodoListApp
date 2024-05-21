package com.todo.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.todo.data.model.Todo


@Composable
fun TodoListScreen(
    navController: NavHostController,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todoList = viewModel.todoList.collectAsState(initial = emptyList())

    TodoListContent(navController, todoList = { todoList.value })
}


@Composable
fun TodoListContent(navController: NavHostController, todoList: () -> List<Todo>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TODO List") },
                actions = {
                    TextField(
                        value = "searchQuery",
                        onValueChange = { /*viewModel.updateSearchQuery(it)*/ },
                        placeholder = { Text("Search TODOs") }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("todoDetails") }) {
                Icon(Icons.Default.Add, contentDescription = "Add TODO")
            }
        },
        content = {
            if (todoList().isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Press the + button to add a TODO item")
                }
            } else {
                LazyColumn {
                    items(todoList()) {
                        Text(it.description)
                    }
                }
            }
        }
    )
}


@Composable
@Preview(showBackground = false)
fun TodoListContentPreview() {
    TodoListContent(navController = NavHostController(LocalContext.current), todoList = { emptyList() })
}