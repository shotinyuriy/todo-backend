package com.shotin.todobackend.rest;

import com.shotin.todobackend.model.ServiceResult;
import com.shotin.todobackend.model.Todo;
import com.shotin.todobackend.model.TodoList;
import com.shotin.todobackend.rest.model.TodoListInfo;
import com.shotin.todobackend.rest.model.TodoListRequest;
import com.shotin.todobackend.rest.model.TodoListsResponse;
import com.shotin.todobackend.rest.model.TodoRequest;
import com.shotin.todobackend.service.TodoListService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo-lists")
public class TodoRestResource {

    private static final String TODO_LIST_URI = "/todo-lists";
    private static final String TODO_URI = "/todos";

    private final TodoListService todoListService;

    public TodoRestResource(@Autowired TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @ApiOperation(value = "Создает новый список дел")
    @PostMapping
    public ResponseEntity<Object> createTodoList(@RequestBody TodoListRequest todoListRequest) {
        if (todoListRequest != null) {
            ServiceResult<TodoList> creationResult = todoListService.createTodoList(todoListRequest.asTodoList());
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

    @ApiOperation(value = "Возвращает краткую информацию обо всех списках дел", response = TodoListsResponse.class)
    @GetMapping
    public ResponseEntity<Object> getTodoLists() {
        List<TodoList> allTodoLists = todoListService.findAllTodoLists();

        List<TodoListInfo> todoListInfos = allTodoLists.stream()
                .map(todoList -> new TodoListInfo(todoList))
                .collect(Collectors.toList());
        TodoListsResponse todoListsResponse = new TodoListsResponse();
        todoListsResponse.setTodoLists(todoListInfos);
        return ResponseEntity.ok(todoListsResponse);
    }

    @ApiOperation(value = "Возвращает подробную информацию о списке дел по todoListId", response = TodoList.class)
    @GetMapping("/{todoListId}")
    public ResponseEntity<Object> getTodoListById(@PathVariable Long todoListId) {
        Optional<TodoList> todoList = todoListService.findTodoListById(todoListId);
        if (todoList.isPresent()) {
            return ResponseEntity.ok(todoList.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Добавляет новое дело в указанный список")
    @PostMapping("/{todoListId}/todos")
    public ResponseEntity<Object> createTodoForTodoListById(@PathVariable Long todoListId, @RequestBody TodoRequest todoRequest) {
        Optional<TodoList> todoList = todoListService.findTodoListById(todoListId);
        if (todoList.isPresent()) {
            ServiceResult<Todo> creationResult = todoListService.createTodoForTodoList(todoList.get(), todoRequest.asTodo());
            if (creationResult.hasError()) {
                return ResponseEntity.badRequest().body(creationResult);
            } else {
                URI createdTodoUri = URI.create(
                        TODO_LIST_URI + "/" + todoList.get().getId() + TODO_URI + "/" + creationResult.getEntity().getId());
                return ResponseEntity.created(createdTodoUri).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Удаляет дело по todoId из списка")
    @DeleteMapping("/{todoListId}/todos/{todoId}")
    public ResponseEntity<Object> deleteTodoFromTodoListById(@PathVariable Long todoListId, @PathVariable Long todoId) {
        Optional<TodoList> todoList = todoListService.findTodoListById(todoListId);
        if (todoList.isPresent()) {
            boolean deleted = todoListService.deleteTodoById(todoList.get(), todoId);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
