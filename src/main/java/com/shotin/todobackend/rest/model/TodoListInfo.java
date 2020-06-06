package com.shotin.todobackend.rest.model;

import com.shotin.todobackend.model.TodoList;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoListInfo {
    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public TodoListInfo(TodoList todoList) {
        this.id = todoList.getId();
        this.title = todoList.getTitle();
        this.createdAt = todoList.getCreatedAt();
    }
}
