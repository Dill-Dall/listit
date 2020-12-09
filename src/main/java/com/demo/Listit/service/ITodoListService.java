package com.demo.Listit.service;

import com.demo.Listit.api.controller.CategoryEnum;
import com.demo.Listit.api.viewmodel.TodoItemRequestModel;
import com.demo.Listit.api.viewmodel.TodoListResponseModel;
import com.demo.Listit.exception.TodoListException;

import java.util.Optional;

public interface ITodoListService {
    Long createNewTodoList(String title);

    Long addNewListItemToTodoList(TodoItemRequestModel listItem) throws TodoListException;

    void deleteTodoItem(long listItemId);

    Optional<TodoListResponseModel> getList(long id);

    Optional<TodoListResponseModel> getFilteredList(long id, CategoryEnum categoryEnum);
}
