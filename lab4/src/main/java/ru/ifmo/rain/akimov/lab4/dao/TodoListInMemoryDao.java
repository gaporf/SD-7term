package ru.ifmo.rain.akimov.lab4.dao;

import ru.ifmo.rain.akimov.lab4.model.TodoList;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TodoListInMemoryDao implements TodoListDao {
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final List<TodoList> todoLists = new CopyOnWriteArrayList<>();

    @Override
    public int addTodoList(TodoList todoList) {
        final int id = lastId.incrementAndGet();
        todoList.setId(id);
        todoLists.add(todoList);
        return id;
    }

    @Override
    public List<TodoList> getTodoLists() {
        return List.copyOf(todoLists);
    }
}
