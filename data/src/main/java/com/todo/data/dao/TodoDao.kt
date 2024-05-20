package com.todo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.todo.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todo: Todo)

    @Query("SELECT * FROM todos")
    fun getTodos(): Flow<List<Todo>>
}