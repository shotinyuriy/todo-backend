package com.shotin.todobackend.repository;

import com.shotin.todobackend.model.TodoList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicLong;

@Repository
public interface TodoListRepository extends CrudRepository<TodoList, Long>{
    static AtomicLong ID_GENERATOR = new AtomicLong(1);
}
