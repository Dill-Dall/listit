package com.demo.Listit.service;

import com.demo.Listit.api.controller.CategoryEnum;
import com.demo.Listit.api.viewmodel.TodoItemRequestModel;
import com.demo.Listit.api.viewmodel.TodoListResponseModel;
import com.demo.Listit.data.model.TodoItem;
import com.demo.Listit.data.repository.TodoItemRepository;
import com.demo.Listit.data.model.TodoList;
import com.demo.Listit.data.repository.TodoListRepository;
import com.demo.Listit.exception.TodoListException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Handles both TodoItem and TodoList services
 */
@Service
public class TodoListService {

    TodoListRepository todoListRepository;
    TodoItemRepository todoItemRepository;
    TodoFactory todoFactory;

    @Inject
    public TodoListService(TodoListRepository todoListRepository, TodoItemRepository todoItemRepository, TodoFactory todoFactory) {
        this.todoListRepository = todoListRepository;
        this.todoItemRepository = todoItemRepository;
        this.todoFactory = todoFactory;
    }

    public Long createNewTodoList(String title) {
        TodoList newListIt = TodoList.builder().title(title).build();
        TodoList todoList = todoListRepository.save(newListIt);
        return todoList.getId();
    }

    public Long addNewListItemToTodoList(TodoItemRequestModel listItem) throws TodoListException {
        TodoItem newTodoItem = todoFactory.createTodoItem(listItem);
        Optional<TodoList> optTodoList = todoListRepository.findById(listItem.getListId());
        if (optTodoList.isPresent()) {
            TodoList todoList = optTodoList.get();

            if (todoFactory.compareForDuplicates(todoList.getTodoItems(), newTodoItem)) {
                throw new TodoListException("Could not add listItem to list. Identical item already exists");
            }
            TodoItem todoItem = saveNewTodoItemToRepos(newTodoItem, todoList);
            return todoItem.getId();
        }

        throw new TodoListException("Could not add listItem to list. Does the List which you want to add to exist?");
    }

    public void deleteTodoItem(long listItemId) {
        Optional<TodoItem> todoItemOptional = todoItemRepository.findById(listItemId);
        if (todoItemOptional.isPresent()) {
            TodoItem todoItem = todoItemOptional.get();
            TodoList todolist = todoItem.getTodoList();
            todolist.deleteTodoItem(todoItem);
            todoListRepository.save(todolist);
            todoItemRepository.delete(todoItem);
        }
    }

    public Optional<TodoListResponseModel> getList(long id) {
        Optional<TodoList> optionalListItList = todoListRepository.findById(id);
        return todoFactory.createOptionalListResponseModel(optionalListItList);
    }

    public Optional<TodoListResponseModel> getFilteredList(long id, CategoryEnum categoryEnum) {
        Optional<TodoList> optionalListItList = todoListRepository.findById(id);
        return todoFactory.createListWithItemsFilteredOnCategory(optionalListItList, categoryEnum);
    }

    private TodoItem saveNewTodoItemToRepos(TodoItem newTodoItem, TodoList todoList) {
        todoList.addNewTodoItem(newTodoItem);
        newTodoItem.setTodoList(todoList);
        TodoItem todoItem = todoItemRepository.save(newTodoItem);
        todoListRepository.save(todoList);
        return todoItem;
    }
}
