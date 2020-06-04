package com.shotin.todobackend.repository;

import com.shotin.todobackend.model.TodoList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends CrudRepository<TodoList, Long>{

}
