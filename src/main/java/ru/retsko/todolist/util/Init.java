package ru.retsko.todolist.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.retsko.todolist.model.entity.Task;
import ru.retsko.todolist.model.enums.TaskStatus;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class Init {
    @PersistenceContext
    EntityManager entityManager;

    @EventListener(ApplicationStartedEvent.class)
    @Transactional
    public void init() throws IOException {
        for (long i = 0L; i < 20L; i++) {
            Task task = Task
                    .builder()
                    .title("Задача №" + i)
                    .start(LocalDateTime.now().plusDays((long) (1 + Math.random() * 20)))
                    // .end(LocalDateTime.now().plusDays((long) (1 + Math.random() * 20)))
                    .status(TaskStatus.CREATED)
                    .description("Описание для Задачи №" + i)
                    .build();
            entityManager.persist(task);
        }
        for (long i = 0L; i < 8L; i++) {
            Task task = Task
                    .builder()
                    .title("Выполненная Задача №" + i)
                    .start(LocalDateTime.now().plusDays((long) (1 + Math.random() * 20)))
                    // .end(LocalDateTime.now().plusDays((long) (1 + Math.random() * 20)))
                    .status(TaskStatus.COMPLETED)
                    .description("Описание для выполненной Задачи №" + i)
                    .build();
            entityManager.persist(task);
        }
    }
}
