package com.omkar.hadpad.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey



// This is a table schema

@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val priority: Int,
    val dueDate: Long
)