package com.demo.Listit.api.viewmodel;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TodoListResponseModel {
    long id;
    String title;
    List<TodoItemResponseModel> listItems;
}
