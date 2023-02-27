package ru.retsko.todolist.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.retsko.todolist.dao.TaskRepository;
import ru.retsko.todolist.exception.CheckStatusException;
import ru.retsko.todolist.exception.ResourceNotFoundException;
import ru.retsko.todolist.mappers.TaskMapper;
import ru.retsko.todolist.model.dto.TaskDto;
import ru.retsko.todolist.model.entity.Task;
import ru.retsko.todolist.model.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @PersistenceContext
    EntityManager entityManager;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDto> getAllTask() {
        return taskMapper.toDtoList(taskRepository.findAll());
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskMapper.toDto(loadTask(id));
    }

    @Override
    public List<TaskDto> getFilteredTask(LocalDateTime startFilterDate, LocalDateTime endFilterDate, TaskStatus taskStatus) {

        return taskMapper.toDtoList(taskRepository.findByStartBetweenAndStatus(startFilterDate, endFilterDate.plusDays(1), taskStatus));
    }

    @Override
    public void addNewTask(TaskDto taskDto) {
        taskDto.setStatus(TaskStatus.CREATED);
        taskRepository.save(taskMapper.toModel(taskDto));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.delete(loadTask(id));
    }

    @Transactional
    @Override
    public void executeTask(Long id) {
        Task taskToUpdate = loadTask(id);
        if (taskToUpdate.getStatus() == TaskStatus.COMPLETED) {
            throw new CheckStatusException("Статус у задачи с Id № " + id + " уже COMPLETED");
        } else {
            taskToUpdate.setStatus(TaskStatus.COMPLETED);
            entityManager.flush();
        }
    }

    @Transactional
    @Override
    public void editTask(TaskDto taskDto) {
        entityManager.merge(taskMapper.toModel(taskDto));
        entityManager.flush();
    }

    @Override
    public List<TaskDto> getTop10TaskByStatus(TaskStatus taskStatus) {
        return taskMapper.toDtoList(taskRepository.findTop10ByStatus(taskStatus));
    }

    private Task loadTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Задача с ID № " + id + " не найдена"));
    }
}
