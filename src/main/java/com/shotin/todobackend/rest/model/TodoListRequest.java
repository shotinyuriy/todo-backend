package com.shotin.todobackend.rest.model;

import com.shotin.todobackend.model.TodoList;
import lombok.Data;

@Data
public class TodoListRequest {
    private String title;

    public TodoList asTodoList() {
        TodoList todoList = new TodoList();
        todoList.setTitle(title);
        return todoList;
    }
}
