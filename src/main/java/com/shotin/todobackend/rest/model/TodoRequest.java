package com.shotin.todobackend.rest.model;

import com.shotin.todobackend.model.Todo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoRequest {
    private String description;
    private LocalDateTime dueDate;

    public Todo asTodo() {
        Todo todo = new Todo();
        todo.setDescription(description);
        todo.setDueDate(dueDate);
        return todo;
    }
}
