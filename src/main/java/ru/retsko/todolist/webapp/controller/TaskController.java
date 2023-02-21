package ru.retsko.todolist.webapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.retsko.todolist.model.dto.TaskDto;
import ru.retsko.todolist.model.entity.Task;
import ru.retsko.todolist.model.enums.TaskStatus;
import ru.retsko.todolist.service.TaskServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    ResponseEntity<List<TaskDto>> getAllTask() {

        return ResponseEntity.ok(taskService.getAlltask());
    }

    @GetMapping("/filtered")
    ResponseEntity<List<TaskDto>> getFilteredTask(@RequestParam(name = "startfilterdate", defaultValue = "") LocalDateTime startfilterdate,
                                                  @RequestParam(name = "endfilterdate", defaultValue = "") LocalDateTime endfilterdate) {

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

        return ResponseEntity.ok(taskService.getTop10TaskbyStatus(TaskStatus.COMPLETED));
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
