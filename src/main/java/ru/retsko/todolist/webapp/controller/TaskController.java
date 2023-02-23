package ru.retsko.todolist.webapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.retsko.todolist.model.dto.TaskDto;
import ru.retsko.todolist.model.enums.TaskStatus;
import ru.retsko.todolist.service.TaskServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    ResponseEntity<List<TaskDto>> getAllTask() {

        return ResponseEntity.ok(taskService.getAllTask());
    }

    @GetMapping("/filtered")
    ResponseEntity<List<TaskDto>> getFilteredTask(@RequestParam(name = "startfilterdate") LocalDateTime startfilterdate,
                                                  @RequestParam(name = "endfilterdate") LocalDateTime endfilterdate) {

        return ResponseEntity.ok(taskService.getFilteredTask(startfilterdate, endfilterdate, TaskStatus.CREATED));
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskDto> getTaskById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/addnewtask")
    void addNewTask(@Valid @RequestBody TaskDto taskDto) {
        taskService.addNewTask(taskDto);
    }

    @DeleteMapping("/deletetask")
    void deleteTask(@RequestParam(name = "id", defaultValue = "") Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/toptencompleted")
    ResponseEntity<List<TaskDto>> getTop10CompletedTask() {

        return ResponseEntity.ok(taskService.getTop10TaskByStatus(TaskStatus.COMPLETED));
    }

    @PatchMapping("/executetask")
    void executeTask(@RequestParam(name = "id", defaultValue = "") Long id) {
        taskService.executeTask(id);
    }

    @PostMapping("/edittask")
    void editTask(@Valid @RequestBody TaskDto taskDto) {
        taskService.editTask(taskDto);
    }
}
