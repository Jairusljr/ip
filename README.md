# Buddy User Guide
Buddy is a lightweight, command-line chatbot designed to help you organize your life with the loyalty of a golden retriever. 
Whether it's a simple task, a strict deadline, or an upcoming event, Buddy's got your back (and your list).

// Product screenshot goes here

// Product intro goes here

---

## Table of Contents
* [Quick Start](#quick-start)
* [Features](#features)
    * [Adding a todo: `todo`](#adding-a-todo-todo)
    * [Adding a deadline: `deadline`](#adding-a-deadline-deadline)
    * [Adding an event: `event`](#adding-an-event-event)
    * [Locating tasks by name: `find`](#locating-tasks-by-name-find)
    * [Listing all tasks: `list`](#listing-all-tasks-list)
    * [Marking a task as done: `mark`](#marking-a-task-as-done-mark)
    * [Marking a task as incomplete: `unmark`](#marking-a-task-as-incomplete-unmark)
    * [Deleting a task: `delete`](#deleting-a-task-delete)
    * [Exiting the program: `bye`](#exiting-the-program-bye)
* [Command Summary](#command-summary)

---

## Quick start
1. Ensure you have Java 11 or above installed on your Computer.
2. Download the latest `buddy.jar` from here.
3. Copy the file to the folder you want to use as the home folder for your Task Tracker.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar buddy.jar` command to run the application. e.g., typing list and pressing Enter will list all your current tasks.
5. Buddy will greet you! Type your commands and press Enter to execute them.

---

## Features

### Command Format Rules
- **Parameter Convention**: Words in `UPPER_CASE` are the parameters to be supplied by the user.  
  *e.g. in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo join social club`.*
- **Case Sensitivity**: Command words (e.g., `todo`, `deadline`) are case-insensitive. `TODO` and `todo` work the same way.
- **Index Values**: For commands requiring an `INDEX` (like `mark` or `delete`), use the number shown in the most recent `list` output. These are **1-based** (1, 2, 3...).
- **Extraneous Parameters**: For commands that do not take in parameters (such as `list` and `bye`), any extra text provided will be ignored.
- **Order Matters**: Flags like `/by`, `/from`, and `/to` act as delimiters. They must be present for the command to be parsed correctly.

---

### Adding a todo: `todo`
Adds a basic task to the task list.
**Format**: `todo DESCRIPTION`
**Example**: `todo join social club`

### Adding a deadline: `deadline`
Adds a task with a specific completion date.
**Format**: `deadline DESCRIPTION /by YYYY-MM-DD`
- The date must strictly follow the `YYYY-MM-DD` format.
**Example**: `deadline submit report /by 2026-03-01`

### Adding an event: `event`
Adds a task that occurs within a specific time frame.
**Format**: `event DESCRIPTION /from START /to END`
**Example**: `event project meeting /from Mon 2pm /to 4pm`

### Locating tasks by name: `find`
Finds tasks whose descriptions contain the given keyword.
**Format**: `find KEYWORD`
**Example**: `find book`

### Listing all tasks: `list`
Shows a list of all tasks currently in Buddy's memory.
**Format**: `list`

### Marking a task as done: `mark`
Marks the specified task from the list as completed.
**Format**: `mark INDEX`
- Marks the task at the specified `INDEX`.
- The index refers to the index number shown in the displayed task list.
- The index **must be a positive integer** 1, 2, 3, …
**Example**: `mark 2`

### Marking a task as incomplete: `unmark`
Marks the specified task from the list as incomplete.
**Format**: `unmark INDEX`
- Marks the task at the specified `INDEX` as not done yet.
- The index refers to the index number shown in the displayed task list.
- The index **must be a positive integer** 1, 2, 3, …
**Example**: `unmark 2`

### Deleting a task: `delete`
Deletes the specified task from the list.
**Format**: `delete INDEX`
- Deletes the task at the specified `INDEX`.
- The index refers to the index number shown in the displayed task list.
- The index **must be a positive integer** 1, 2, 3, …
**Example**: `delete 1`

### Exiting the program: `bye`
Exits the program and ensures all data is safely saved.
**Format**: `bye`

---

## Command Summary

| Action | Format | Example |
| :--- | :--- | :--- |
| **Add Todo** | `todo DESCRIPTION` | `todo buy bread` |
| **Add Deadline** | `deadline DESCRIPTION /by YYYY-MM-DD` | `deadline iP /by 2026-02-27` |
| **Add Event** | `event DESCRIPTION /from START /to END` | `event meeting /from 2pm /to 4pm` |
| **Locate** | `find KEYWORD` | `find book` |
| **List** | `list` | `list` |
| **Mark** | `mark INDEX` | `mark 1` |
| **Unmark** | `unmark INDEX` | `unmark 1` |
| **Delete** | `delete INDEX` | `delete 2` |
| **Exit** | `bye` | `bye` |
