package com.demo.Listit.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(targetEntity = TodoItem.class)
    private List<TodoItem> todoItems;

    public void addNewTodoItem(TodoItem todoItem) {
        todoItems.add(todoItem);
    }

    public void deleteTodoItem(TodoItem todoItem) {
        todoItems.remove(todoItem);
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", todoItems=" + todoItems.size() +
                '}';
    }
}
