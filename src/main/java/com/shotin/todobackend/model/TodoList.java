package com.shotin.todobackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TodoList implements Serializable {
    @Id
    private Long id;
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String title;
    private LocalDateTime createdAt;
    private List<Todo> todos = new ArrayList<>();
}
