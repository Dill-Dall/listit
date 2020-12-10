package com.demo.Listit.data.model;

import com.demo.Listit.api.controller.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textField  ="";

    @ElementCollection
    private Set<CategoryEnum> categories;

    @JoinColumn(name = "todo_list_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TodoList todoList;


    /**
     * Creating this non overiding Equal mehthod, to check for equality in selected values. not including Id.
     *
     * @return boolean
     */
    public boolean isEqual(TodoItem todoItem) {
        return textField.equals(todoItem.textField)
                && categories.equals(todoItem.categories);
    }

}
