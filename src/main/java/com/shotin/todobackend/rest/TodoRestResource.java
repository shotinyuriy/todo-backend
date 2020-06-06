package com.shotin.todobackend.rest;

import com.shotin.todobackend.model.ServiceResult;
import com.shotin.todobackend.model.Todo;
import com.shotin.todobackend.model.TodoList;
import com.shotin.todobackend.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo-lists")
public class TodoRestResource {

    private static final String TODO_LIST_URI = "/todo-lists";
    private static final String TODO_URI = "/todos";

    private final TodoListService todoListService;

    public TodoRestResource(@Autowired TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping
    public ResponseEntity<Object> createTodoList(@RequestBody TodoList todoList) {
        if (todoList != null) {
            ServiceResult<TodoList> creationResult = todoListService.createTodoList(todoList);
            if (creationResult.hasError()) {
                return ResponseEntity.badRequest().body(creationResult);
            } else {
                URI createdTodoListUri = URI.create(TODO_LIST_URI + "/" + creationResult.getEntity().getId());
                return ResponseEntity.created(createdTodoListUri).build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Object> getTodoLists() {
        List<TodoList> allTodoLists = todoListService.findAllTodoLists();
        TodoListsResponse todoListsResponse = new TodoListsResponse();
        todoListsResponse.setTodoLists(allTodoLists);
        return ResponseEntity.ok(todoListsResponse);
    }

    @GetMapping("/{todoListId}")
    public ResponseEntity<Object> getTodoListById(@PathVariable Long todoListId) {
        Optional<TodoList> todoList = todoListService.findTodoListById(todoListId);
        if (todoList.isPresent()) {
            return ResponseEntity.ok(todoList.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{todoListId}/todos")
    public ResponseEntity<Object> createTodoForTodoListById(@PathVariable Long todoListId, @RequestBody Todo todo) {
        Optional<TodoList> todoList = todoListService.findTodoListById(todoListId);
        if (todoList.isPresent()) {
            ServiceResult<Todo> creationResult = todoListService.createTodoForTodoList(todoList.get(), todo);
            if (creationResult.hasError()) {
                return ResponseEntity.badRequest().body(creationResult);
            } else {
                URI createdTodoUri = URI.create(TODO_LIST_URI + "/" + TODO_URI + "/" + creationResult.getEntity().getId());
                return ResponseEntity.created(createdTodoUri).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
