package com.todo.feature.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.data.model.Todo
import com.todo.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _errorEvent = MutableStateFlow<String?>(null)
    val errorEvent: StateFlow<String?> = _errorEvent

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val debouncedSearchQuery: StateFlow<String> =
        _searchQuery.debounce(2000).stateIn(viewModelScope, SharingStarted.Lazily, "")

    private val _todo = mutableStateOf("")
    val todo: State<String> = _todo

    private val _isTodoError = mutableStateOf(false)
    val isTodoError = _isTodoError

    private val _addTodoInProgress = mutableStateOf(false)
    val addTodoInProgress: State<Boolean> = _addTodoInProgress

    val todoList: StateFlow<List<Todo>> = repository.getTodos()
        .combine(debouncedSearchQuery) { todos, query ->
            if (query.isEmpty()) {
                todos
            } else {
                todos.filter { it.description.contains(query, ignoreCase = true) }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    internal fun onTodoValueChange(text: String) {
        _todo.value = text
    }

    internal fun clearErrorEvent() {
        _errorEvent.value = null
    }

    internal fun setSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    internal fun addTodo(onComplete: () -> Unit) {
        if (validationSuccess()) {
            viewModelScope.launch {
                try {
                    if (_todo.value.equals("Error", ignoreCase = true)) {
                        throw IllegalArgumentException()
                    }
                    _addTodoInProgress.value = true
                    delay(3000)
                    repository.insert(Todo(description = _todo.value))
                    _addTodoInProgress.value = false
                    onComplete()
                } catch (e: Exception) {
                    _addTodoInProgress.value = false
                    _errorEvent.update { "Failed to add TODO" }
                    onComplete()
                }
            }
        }
    }

    private fun validationSuccess(): Boolean {
        val todoIsNotEmpty = _todo.value.isNotEmpty()
        _isTodoError.value = !todoIsNotEmpty
        return todoIsNotEmpty
    }
}
