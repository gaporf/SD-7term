package ru.ifmo.rain.akimov.lab4.dao;

import ru.ifmo.rain.akimov.lab4.model.TodoList;

import java.util.List;

public interface TodoListDao {
    int addTodoList(final TodoList todoList);

    List<TodoList> getTodoLists();
}
