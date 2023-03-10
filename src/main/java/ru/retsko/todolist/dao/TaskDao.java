package ru.retsko.todolist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.retsko.todolist.model.entity.Task;
import ru.retsko.todolist.model.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskDao extends JpaRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {

    List<Task> findByStartBetweenAndStatus(LocalDateTime startfilterdate, LocalDateTime endfilterdate, TaskStatus taskStatus);
    List<Task> findTop10ByStatus(TaskStatus taskStatus);

}
