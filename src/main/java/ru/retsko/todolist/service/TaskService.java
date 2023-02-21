package ru.retsko.todolist.service;

import ru.retsko.todolist.model.dto.TaskDto;
import ru.retsko.todolist.model.entity.Task;
import ru.retsko.todolist.model.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    List<TaskDto> getAlltask();

    TaskDto getTaskById(Long id);

    List<TaskDto> getFilteredTask(LocalDateTime startfilterdate, LocalDateTime endfilterdate, TaskStatus taskStatus);

    void addNewTask(TaskDto taskDto);

    void deleteTask(Long id);

    void executeTask(Long id);
    void editTask(TaskDto taskDto);

    List<TaskDto> getTop10TaskbyStatus(TaskStatus taskStatus);
}
