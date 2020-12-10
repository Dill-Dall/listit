package com.demo.Listit.service;

import com.demo.Listit.api.controller.CategoryEnum;
import com.demo.Listit.api.viewmodel.TodoItemRequestModel;
import com.demo.Listit.api.viewmodel.TodoItemResponseModel;
import com.demo.Listit.api.viewmodel.TodoListResponseModel;
import com.demo.Listit.data.model.TodoItem;
import com.demo.Listit.data.model.TodoList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TodoFactory {

    public Optional<TodoListResponseModel> createOptionalListResponseModel(Optional<TodoList> optionalListItList) {
        if (optionalListItList.isPresent()) {
            TodoList todoList = optionalListItList.get();
            List<TodoItem> listItItemList = todoList.getTodoItems();
            return getTodoListResponseModel(todoList, listItItemList);
        }
        return Optional.empty();
    }

    public TodoItem createTodoItem(TodoItemRequestModel listItem) {
        return TodoItem.builder()
                .textField(listItem.getTextField())
                .categories(new HashSet<>(listItem.getCategory()))
                .build();
    }

    public Optional<TodoListResponseModel> createListWithItemsFilteredOnCategory(Optional<TodoList> optionalListItList,
                                                                                 CategoryEnum categoryEnum) {
        if (optionalListItList.isPresent()) {
            TodoList todoList = optionalListItList.get();
            List<TodoItem> listItItemList = todoList.getTodoItems().stream()
                    .filter(item -> item.getCategories().contains(categoryEnum)).collect(Collectors.toList());

            return getTodoListResponseModel(todoList, listItItemList);
        }
        return Optional.empty();

    }

    private Optional<TodoListResponseModel> getTodoListResponseModel(TodoList todoList, List<TodoItem> listItItemList) {
        List<TodoItemResponseModel> todoItemResponseModels = listItItemList.stream()
                .map(it -> TodoItemResponseModel.builder()
                        .id(it.getId())
                        .categories(new ArrayList<>(it.getCategories()))
                        .textField(it.getTextField())
                        .build())
                .collect(Collectors.toList());

        return Optional.of(TodoListResponseModel.builder()
                .id(todoList.getId())
                .title(todoList.getTitle())
                .listItems(todoItemResponseModels)
                .build());
    }

    public boolean compareForDuplicates(List<TodoItem> todoList, TodoItem todoItem) {
        return todoList.stream().anyMatch(it -> it.isEqual(todoItem));
    }
}
