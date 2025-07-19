package com.todoproject.demo.mapper;

import com.todoproject.demo.dto.request.TaskRequestDto;
import com.todoproject.demo.dto.response.TaskResponseDto;
import com.todoproject.demo.model.Task;
import com.todoproject.demo.model.TaskState;
import com.todoproject.demo.model.Priority;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TaskResponseDto convertToDto(Task task) {
        TaskResponseDto dto = modelMapper.map(task, TaskResponseDto.class);

        dto.setId(task.getId());

        // Convertir enums a String
        dto.setState(task.getState() != null ? task.getState().name() : null);
        dto.setPriority(task.getPriority() != null ? task.getPriority().name() : null);

        mapProjectDetails(task, dto);
        mapAssignedUserDetails(task, dto);

        return dto;
    }


    private void mapProjectDetails(Task task, TaskResponseDto dto) {
        if (task.getProject() != null) {
            dto.setProjectCode(task.getProject().getCode());
            dto.setProjectName(task.getProject().getName());
        }
    }

    private void mapAssignedUserDetails(Task task, TaskResponseDto dto) {
        if (task.getAssignedTo() != null) {
            dto.setAssignedUserId(task.getAssignedTo().getId());
            dto.setAssignedUserAvatarUrl(task.getAssignedTo().getAvatarUrl());
            dto.setAssignedUserName(task.getAssignedTo().getName());
        }
    }

    public Task convertToEntity(TaskRequestDto taskRequestDto) {
        Task task = modelMapper.map(taskRequestDto, Task.class);

        // Convertir Strings a enums
        if (taskRequestDto.getState() != null) {
            task.setState(TaskState.valueOf(taskRequestDto.getState()));
        } else {
            task.setState(null);
        }

        if (taskRequestDto.getPriority() != null) {
            task.setPriority(Priority.valueOf(taskRequestDto.getPriority()));
        } else {
            task.setPriority(null);
        }

        return task;
    }
}
