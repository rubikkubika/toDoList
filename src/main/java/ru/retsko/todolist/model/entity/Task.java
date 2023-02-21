package ru.retsko.todolist.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.retsko.todolist.model.enums.TaskStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private TaskStatus status;
    @Column(name = "end_task")
    private LocalDateTime end;
    @Column(name = "start_task")
    private LocalDateTime start;
}
