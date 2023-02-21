package ru.retsko.todolist.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.retsko.todolist.dao.TaskDao;
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
    private final TaskDao taskDao;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskDao taskDao, TaskMapper taskMapper) {
        this.taskDao = taskDao;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDto> getAlltask() {
        return taskMapper.toDtoList(taskDao.findAll());
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskMapper.toDto(taskDao.findById(id).get());
    }

    @Override
    public List<TaskDto> getFilteredTask(LocalDateTime startfilterdate, LocalDateTime endfilterdate, TaskStatus taskStatus) {

        endfilterdate = endfilterdate.plusDays(1);
        return taskMapper.toDtoList(taskDao.findByStartBetweenAndStatus(startfilterdate, endfilterdate, taskStatus));
    }

    @Override
    public void addNewTask(TaskDto taskDto) {
        taskDto.setStatus(TaskStatus.CREATED);
        taskDao.save(taskMapper.toModel(taskDto));
    }

    @Override
    public void deleteTask(Long id) {
        taskDao.deleteById(id);
    }

    @Override
    public void executeTask(Long id) {
        taskDao.findById(id).get().setStatus(TaskStatus.COMPLETED);
        taskDao.flush();
    }

    @Transactional
    @Override
    public void editTask(TaskDto taskDto) {
        entityManager.merge(taskMapper.toModel(taskDto));
        entityManager.flush();
    }

    @Override
    public List<TaskDto> getTop10TaskbyStatus(TaskStatus taskStatus) {
        return taskMapper.toDtoList(taskDao.findTop10ByStatus(taskStatus));
    }
}
