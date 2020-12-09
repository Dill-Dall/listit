package com.demo.Listit.api.viewmodel;

import com.demo.Listit.api.controller.CategoryEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TodoItemResponseModel {
    long id;
    String textField;
    List<CategoryEnum> categories;
}
