package ru.ifmo.rain.akimov.lab4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.rain.akimov.lab4.dao.TodoListDao;
import ru.ifmo.rain.akimov.lab4.dao.TodoListInMemoryDao;

@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TodoListDao todoListDao() {
        return new TodoListInMemoryDao();
    }
}
