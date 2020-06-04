package com.shotin.todobackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class TodoList implements Serializable {
    @Id
    private Long id;
    private String title;
}
