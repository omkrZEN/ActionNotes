package com.omkar.hadpad



/*
========================================
ROOM DATABASE - PROJECT LEVEL NOTES
========================================

We created 4 main files:

1. Task.kt  (Entity)
----------------------------------------
- Represents a table in the database (tasks table)
- Each Task object = one row

Fields:
- id → Primary key (auto-generated)
- title → Task title
- description → Details
- isCompleted → Completion status
- priority → Task priority (1,2,3)
- dueDate → Stored as Long (timestamp)

WHY:
- Defines structure of data stored in DB


2. TaskDao.kt  (DAO - Data Access Object)
----------------------------------------
Functions:
- insertTask() → add new task
- updateTask() → modify task
- deleteTask() → remove task
- getAllTasks() → fetch all tasks

Special:
- Uses annotations like @Insert, @Query
- Returns Flow<List<Task>>

WHY:
- Separates database operations from rest of app
- Flow allows automatic updates when data changes


3. AppDatabase.kt  (Database)
----------------------------------------
- Connects Entity (Task) and DAO (TaskDao)
- Extends RoomDatabase

Function:
- taskDao() → gives access to DAO

WHY:
- Acts as main database holder
- Central point for database configuration


4. DatabaseProvider.kt  (Singleton)
----------------------------------------
- Creates only ONE instance of database

Function:
- getDatabase(context)

WHY:
- Prevents multiple DB instances
- Ensures efficiency and avoids crashes


========================================
DATA FLOW (IMPORTANT)
========================================

Room DB → TaskDao → Repository → ViewModel → UI

(Currently we built only till DB + DAO)


========================================
IMPORTANT CONCEPTS
========================================

Flow:
- Observes database changes
- Automatically updates UI when data changes

Suspend Functions:
- Runs database operations in background
- Prevents UI freeze


///////////////////////////////////////////////////////////////////////////////


VIEWMODEL PURPOSE

- Connects UI and Repository
- Lifecycle-aware (survives rotation)
- Holds UI state
- Keeps UI logic separate from data logic


*/
