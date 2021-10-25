package ru.ifmo.rain.akimov.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ifmo.rain.akimov.lab4.dao.TodoListDao;
import ru.ifmo.rain.akimov.lab4.model.TodoList;

import java.util.List;

@Controller
public class TodoListController {
    private final TodoListDao todoListDao;

    public TodoListController(final TodoListDao todoListDao) {
        this.todoListDao = todoListDao;
    }

    @RequestMapping(value = "/add-todolist", method = RequestMethod.POST)
    public String addTodoList(@ModelAttribute("todolist") TodoList todoList) {
        todoListDao.addTodoList(todoList);
        return "redirect:/get-todolists";
    }

    @RequestMapping(value="/get-todolists", method = RequestMethod.GET)
    public String getTodoLists(final ModelMap map) {
        prepareModelMap(map, todoListDao.getTodoLists());
        return "index";
    }

    private void prepareModelMap(final ModelMap map, final List<TodoList> todoLists) {
        map.addAttribute("todolists", todoLists);
        map.addAttribute("todolist", new TodoList());
    }
}
