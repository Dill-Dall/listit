package com.demo.Listit.api.controller;


import com.demo.Listit.api.viewmodel.TodoItemRequestModel;
import com.demo.Listit.api.viewmodel.TodoListResponseModel;
import com.demo.Listit.exception.TodoListException;
import com.demo.Listit.service.TodoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Optional;

@RequestMapping(path = "/todolist")
@RestController
public class ListItRestController {
    TodoListService service;

    @Inject
    public ListItRestController(TodoListService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Long> createNewList(String name){
        Long idOfNewList = service.createNewTodoList(name);
        return new ResponseEntity<>(idOfNewList, HttpStatus.CREATED);
    }

    @PostMapping(path = "/item")
    public  ResponseEntity<Object> createNewListItem(TodoItemRequestModel listItem){
        try {
            Long idOfNewItem = service.addNewListItemToTodoList(listItem);
            return new ResponseEntity<>(idOfNewItem, HttpStatus.CREATED);
        } catch (TodoListException e) {
            return new ResponseEntity<>("Might be listid is false or duplikate object",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/item")
    public ResponseEntity<String> deleteListItem(@RequestParam("listitemvalue") int listItemId){
        service.deleteTodoItem(listItemId);
        return ResponseEntity.ok("Listitem deleted successfullly");
    }

    @GetMapping
    public ResponseEntity<TodoListResponseModel> getList(@RequestParam("Listid") int id){
        Optional<TodoListResponseModel> responseModel = service.getList(id);
        return responseModel.map(todoListResponseModel -> new ResponseEntity<>(todoListResponseModel, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/filtered")
    public ResponseEntity<TodoListResponseModel> getList(@RequestParam("Listid") int id, @RequestParam("category") CategoryEnum categoryEnum){
        Optional<TodoListResponseModel> responseModel = service.getFilteredList(id, categoryEnum);
        return responseModel.map(todoListResponseModel -> new ResponseEntity<>(todoListResponseModel, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}
