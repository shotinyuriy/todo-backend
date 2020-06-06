package com.shotin.todobackend.rest;

import com.shotin.todobackend.model.TodoList;
import lombok.Data;

import java.util.List;

@Data
public class TodoListsResponse {
    List<TodoList> todoLists;
}