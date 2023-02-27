package ru.retsko.todolist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.retsko.todolist.model.entity.Task;
import ru.retsko.todolist.model.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    List<Task> findByStartBetweenAndStatus(LocalDateTime startFilterDate, LocalDateTime endFilterDate, TaskStatus taskStatus);

    List<Task> findTop10ByStatus(TaskStatus taskStatus);
}
