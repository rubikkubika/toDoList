package ru.retsko.todolist.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum TaskStatus {
    CREATED,
    COMPLETED,
    EXPIRED
}
