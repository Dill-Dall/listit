package com.demo.Listit.api.viewmodel;

import com.demo.Listit.api.controller.CategoryEnum;
import lombok.Data;

import java.util.List;

@Data
public class TodoItemRequestModel {
    long listId;
    String textField;
    List<CategoryEnum> category;
}
