package com.omkar.hadpad.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

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