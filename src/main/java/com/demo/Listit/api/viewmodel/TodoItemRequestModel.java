package com.demo.Listit.api.viewmodel;

import com.demo.Listit.api.controller.CategoryEnum;
import lombok.Data;

@Data
public class TodoItemRequestModel {
    long listId;
    String title;
    String textField;
    CategoryEnum category;
}
