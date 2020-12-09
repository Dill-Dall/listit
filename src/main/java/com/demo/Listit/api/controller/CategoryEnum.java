package com.demo.Listit.api.controller;

import lombok.Getter;

@Getter
public enum CategoryEnum {
    WORK("Work"),
    SHOPPING("Shopping"),
    ADMIN("Admin"),
    OTHER("Other"),
    NON("");

    private final String stringValue;

    CategoryEnum(String stringValue) {
        this.stringValue = stringValue;
    }
}
