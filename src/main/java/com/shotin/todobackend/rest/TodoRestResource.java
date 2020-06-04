package com.shotin.todobackend.rest;

import com.shotin.todobackend.model.TodoList;
import com.shotin.todobackend.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/todo-lists")
public class TodoRestResource {

    private final TodoListRepository todoListRepository;

    public TodoRestResource(@Autowired TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createTodoList(@RequestBody TodoList todoList) {
        if (todoList != null) {
            todoList.setId(TodoListRepository.ID_GENERATOR.getAndIncrement());
            todoListRepository.save(todoList);
            URI createdTodoListUri = URI.create("todo-lists/"+todoList);
            return ResponseEntity.created(createdTodoListUri).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
