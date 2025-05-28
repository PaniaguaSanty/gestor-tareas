package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.ProjectRequestDto;
import com.todoproject.demo.dto.response.ProjectResponseDto;
import com.todoproject.demo.util.CRUD;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements CRUD<ProjectResponseDto, ProjectRequestDto> {
    @Override
    public ProjectResponseDto create(ProjectRequestDto projectRequestDto) {
        return null;
    }

    @Override
    public ProjectResponseDto update(ProjectRequestDto projectRequestDto) {
        return null;
    }

    @Override
    public List<ProjectResponseDto> findAll() {
        return List.of();
    }

    @Override
    public ProjectResponseDto findOne(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public ProjectResponseDto disable(String id) {
        return null;
    }
}
