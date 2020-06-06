package com.shotin.todobackend.rest.model;

import com.shotin.todobackend.model.TodoList;
import lombok.Data;

import java.util.List;

@Data
public class TodoListsResponse {
    List<TodoListInfo> todoLists;
}