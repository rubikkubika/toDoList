package ru.retsko.todolist.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.retsko.todolist.dao.TaskRepository;
import ru.retsko.todolist.mappers.TaskMapper;
import ru.retsko.todolist.model.dto.TaskDto;
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
        if (taskRepository.findById(id).isPresent()) {
            return taskMapper.toDto((taskRepository.findById(id).get()));
        }
        return null;
    }

    @Override
    public List<TaskDto> getFilteredTask(LocalDateTime startfilterdate, LocalDateTime endfilterdate, TaskStatus taskStatus) {

        endfilterdate = endfilterdate.plusDays(1);
        return taskMapper.toDtoList(taskRepository.findByStartBetweenAndStatus(startfilterdate, endfilterdate, taskStatus));
    }

    @Override
    public void addNewTask(TaskDto taskDto) {
        taskDto.setStatus(TaskStatus.CREATED);
        taskRepository.save(taskMapper.toModel(taskDto));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void executeTask(Long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.findById(id).get().setStatus(TaskStatus.COMPLETED);
            taskRepository.flush();
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
}
