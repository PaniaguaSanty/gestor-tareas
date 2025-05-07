package com.todoproject.demo.mapper;

import com.todoproject.demo.dto.request.ProjectRequestDto;
import com.todoproject.demo.dto.response.ProjectResponseDto;
import com.todoproject.demo.model.Project;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {

    private final ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProjectResponseDto convertToDto(Project project) {
        return modelMapper.map(project, ProjectResponseDto.class);
    }

    public Project convertToEntity(ProjectRequestDto projectRequestDto) {
        return modelMapper.map(projectRequestDto, Project.class);
    }

}
