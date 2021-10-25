package ru.ifmo.rain.akimov.lab4.model;

import java.util.ArrayList;
import java.util.List;

public class TodoList {
    private int id;
    private final List<TodoItem> todoItems = new ArrayList<>();
    private String name;

    public TodoList() {
    }

    public TodoList(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = name;
    }
}
