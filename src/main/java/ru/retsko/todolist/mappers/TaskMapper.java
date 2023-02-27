package ru.retsko.todolist.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.retsko.todolist.model.dto.TaskDto;
import ru.retsko.todolist.model.entity.Task;

import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TaskMapper {
    TaskDto toDto(Task task);

    Task toModel(TaskDto taskDto);

    List<TaskDto> toDtoList(List<Task> taskList);

    List<Task> toModelList(List<TaskDto> taskDtoList);

}
