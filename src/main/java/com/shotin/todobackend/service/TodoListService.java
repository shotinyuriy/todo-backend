package com.shotin.todobackend.service;

import com.shotin.todobackend.model.ServiceResult;
import com.shotin.todobackend.model.Todo;
import com.shotin.todobackend.model.TodoList;
import com.shotin.todobackend.model.ValidationError;
import com.shotin.todobackend.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TodoListService {

    private final int MAX_ENTITIES = 20;
    private final TodoListRepository todoListRepository;

    private final Validator validator;

    public TodoListService(@Autowired TodoListRepository todoListRepository, @Autowired Validator validator) {
        this.todoListRepository = todoListRepository;
        this.validator = validator;
    }

    public ServiceResult<TodoList> createTodoList(TodoList todoList) {
        if (todoListRepository.count() >= MAX_ENTITIES) {
            ValidationError error = new ValidationError("todoLists.size", "не больше "+MAX_ENTITIES);
            return new ServiceResult<TodoList>(null, Collections.singleton(error));
        }
        todoList.setId(TodoListRepository.TODO_LIST_ID_GENERATOR.getAndIncrement());
        todoList.setCreatedAt(LocalDateTime.now());
        Set<ConstraintViolation<TodoList>> violations = validator.validate(todoList);
        if (violations != null && !violations.isEmpty()) {
            return new ServiceResult<>(null, ViolationConverter.toValidationErrors(violations));
        }
        TodoList todoListCreated = todoListRepository.save(todoList);
        return new ServiceResult<>(todoListCreated, null);
    }

    public List<TodoList> findAllTodoLists() {
        List<TodoList> todoLists = new ArrayList<>();
        todoListRepository.findAll().forEach(todoList -> todoLists.add(todoList));
        return todoLists;
    }

    public Optional<TodoList> findTodoListById(Long todoListId) {
        return todoListRepository.findById(todoListId);
    }

    public ServiceResult<Todo> createTodoForTodoList(TodoList todoList, Todo todo) {
        if (todoList.getTodos().size() >= MAX_ENTITIES) {
            ValidationError error = new ValidationError("todoLists.todos.size", "не больше "+MAX_ENTITIES);
            return new ServiceResult<Todo>(null, Collections.singleton(error));
        }
        todo.setId(todoListRepository.TODO_ID_GENERATOR.getAndIncrement());
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        if (violations != null && !violations.isEmpty()) {
            return new ServiceResult<>(null, ViolationConverter.toValidationErrors(violations));
        } else {
            todoList.getTodos().add(todo);
            todoListRepository.save(todoList);
            return new ServiceResult<>(todo, null);
        }

    }
}
