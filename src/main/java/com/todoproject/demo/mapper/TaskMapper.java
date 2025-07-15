package com.todoproject.demo.mapper;

import com.todoproject.demo.dto.request.TaskRequestDto;
import com.todoproject.demo.dto.response.TaskResponseDto;
import com.todoproject.demo.model.Task;
import org.modelmapper.ModelMapper;

public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TaskResponseDto convertToDto(Task task) {
        return modelMapper.map(task, TaskResponseDto.class);
    }

    public Task convertToEntity(TaskRequestDto taskRequestDto) {
        return modelMapper.map(taskRequestDto, Task.class);
    }
}
