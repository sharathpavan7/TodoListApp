package com.todo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.todo.data.dao.TodoDao
import com.todo.data.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}