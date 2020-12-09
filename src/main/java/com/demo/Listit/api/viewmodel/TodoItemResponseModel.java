package com.demo.Listit.api.viewmodel;

import com.demo.Listit.api.controller.CategoryEnum;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TodoItemResponseModel {
    long id;
    String textField;
    CategoryEnum category;
}
