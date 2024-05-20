package com.todo.todolistapp.di

import android.content.Context
import androidx.room.Room
import com.todo.data.dao.TodoDao
import com.todo.data.database.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext app: Context): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    fun provideTodoDao(db: TodoDatabase): TodoDao {
        return db.todoDao()
    }
}