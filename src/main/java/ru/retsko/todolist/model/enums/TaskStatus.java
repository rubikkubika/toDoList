package ru.retsko.todolist.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus {
    CREATED,
    COMPLETED,
    EXPIRED
}
