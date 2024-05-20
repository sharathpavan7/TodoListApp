package com.todo.data.repository

import com.todo.data.dao.TodoDao
import com.todo.data.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {
    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    fun getTodos(): Flow<List<Todo>> = todoDao.getTodos()
}