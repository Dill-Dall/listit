package com.demo.Listit.api.controller;


import com.demo.Listit.api.viewmodel.TodoItemRequestModel;
import com.demo.Listit.api.viewmodel.TodoListResponseModel;
import com.demo.Listit.exception.TodoListException;
import com.demo.Listit.service.TodoListService;
import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:8080"})
@RequestMapping(path = "/todolist")
@RestController
public class ListItRestController {
    TodoListService service;

    @Inject
    public ListItRestController(TodoListService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Long> createNewList(@RequestParam @NotNull String title) {
        Long idOfNewList = service.createNewTodoList(title);
        return new ResponseEntity<>(idOfNewList, HttpStatus.CREATED);
    }

    @PostMapping(path = "/item")
    public ResponseEntity<Object> createNewListItem(@RequestBody TodoItemRequestModel listItem) {
        try {
            Long idOfNewItem = service.addNewListItemToTodoList(listItem);
            return new ResponseEntity<>(idOfNewItem, HttpStatus.CREATED);
        } catch (TodoListException e) {
            return new ResponseEntity<>("Might be listid is false or duplikate object", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/item")
    public ResponseEntity<String> deleteListItem(@RequestParam("ItemId")  @NotNull int id) {
        service.deleteTodoItem(id);
        return ResponseEntity.ok("Listitem deleted successfullly");
    }

    @GetMapping
    public ResponseEntity<TodoListResponseModel> getList(@RequestParam("Listid") @NotNull int id) {
        Optional<TodoListResponseModel> responseModel = service.getList(id);
        return responseModel.map(todoListResponseModel -> new ResponseEntity<>(todoListResponseModel, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/filtered")
    public ResponseEntity<TodoListResponseModel> getList(@RequestParam("Listid")  @NotNull int id,
                                                         @RequestParam("category") @NotNull CategoryEnum categoryEnum) {
        Optional<TodoListResponseModel> responseModel = service.getFilteredList(id, categoryEnum);
        return responseModel.map(todoListResponseModel -> new ResponseEntity<>(todoListResponseModel, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
