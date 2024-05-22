package com.todo.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.todo.data.model.Todo


@Composable
fun TodoListScreen(
    navController: NavHostController,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todoList = viewModel.todoList.collectAsState(initial = emptyList())
    val searchQuery = viewModel.searchQuery.collectAsState().value

    TodoListContent(
        navController,
        todoList = { todoList.value },
        searchQuery = { searchQuery },
        onSearchChange = { viewModel.setSearchQuery(it) })
}


@Composable
fun TodoListContent(
    navController: NavHostController,
    todoList: () -> List<Todo>,
    searchQuery: () -> String,
    onSearchChange: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.todo_list)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("todoDetails") }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_todo))
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
                    Text(stringResource(R.string.press_the_button_to_add_a_todo_item))
                }
            } else {
                Column(modifier = Modifier.padding(16.dp)) {

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        value = searchQuery(),
                        onValueChange = { onSearchChange(it) },
                        placeholder = { Text(stringResource(R.string.search_todos)) }
                    )

                    LazyColumn {
                        items(todoList()) {
                            Text(
                                it.description,
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
@Preview(showBackground = false)
fun TodoListContentPreview() {
    TodoListContent(
        navController = NavHostController(LocalContext.current),
        todoList = { emptyList() },
        searchQuery = { "" },
        onSearchChange = {})
}