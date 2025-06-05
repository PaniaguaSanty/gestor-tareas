package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.ProjectRequestDto;
import com.todoproject.demo.dto.response.ProjectResponseDto;
import com.todoproject.demo.exception.NotFoundException;
import com.todoproject.demo.mapper.ProjectMapper;
import com.todoproject.demo.model.Project;
import com.todoproject.demo.model.Team;
import com.todoproject.demo.repository.ProjectRepository;
import com.todoproject.demo.repository.TeamRepository;
import com.todoproject.demo.util.CRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements CRUD<ProjectResponseDto, ProjectRequestDto> {

    private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, TeamRepository teamRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectResponseDto create(ProjectRequestDto projectRequestDto) {
        logger.info("Entering create Project...");
        Team team = teamRepository.findByDni(projectRequestDto.getTeamDni())
                .orElseThrow(() -> new NotFoundException("Team not found with DNI: " + projectRequestDto.getTeamDni()));

        Project project = projectMapper.convertToEntity(projectRequestDto);
        project.setActive(true);
        project.setTeam(team);

        String generatedCode;
        do {
            generatedCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        } while (projectRepository.existsByCode(generatedCode));
        project.setCode(generatedCode);

        Project saved = projectRepository.save(project);
        return projectMapper.convertToDto(saved);
    }

    @Override
    public ProjectResponseDto update(ProjectRequestDto projectRequestDto) {
        logger.info("Entering update Project...");

        Project project = projectRepository.findByCode(projectRequestDto.getCode())
                .orElseThrow(() -> new NotFoundException("Project not found with code: " + projectRequestDto.getCode()));

        project.setName(projectRequestDto.getName());
        project.setDescription(projectRequestDto.getDescription());
        project.setStartDate(projectRequestDto.getStartDate());
        project.setFinishDate(projectRequestDto.getFinishDate());

        // Si se desea cambiar el team
        if (projectRequestDto.getTeamDni() != null) {
            Team team = teamRepository.findByDni(projectRequestDto.getTeamDni())
                    .orElseThrow(() -> new NotFoundException("Team not found with DNI: " + projectRequestDto.getTeamDni()));
            project.setTeam(team);
        }

        Project saved = projectRepository.save(project);
        return projectMapper.convertToDto(saved);
    }

    @Override
    public List<ProjectResponseDto> findAll() {
        logger.info("Entering findAll Projects...");
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectResponseDto> findAllActive() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .filter(Project::isActive)
                .map(projectMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDto findOne(String code) {
        logger.info("Entering findOne Project...");
        Project project = projectRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Project not found with code: " + code));
        return projectMapper.convertToDto(project);
    }

    @Override
    public void delete(String code) {
        logger.info("Entering delete Project...");
        Project project = projectRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Project not found with code: " + code));
        projectRepository.delete(project);
    }

    public ProjectResponseDto enable(String code) {
        logger.info("Entering enable Project...");
        Project project = projectRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Project not found with current code..."));
        project.setActive(true);
        projectRepository.save(project);
        return projectMapper.convertToDto(project);
    }

    @Override
    public ProjectResponseDto disable(String code) {
        logger.info("Entering disable Project...");
        Project project = projectRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Project not found with code: " + code));
        project.setActive(false);
        Project saved = projectRepository.save(project);
        return projectMapper.convertToDto(saved);
    }

    public List<ProjectResponseDto> findByTeamDni(String dni) {
        logger.info("Entering findByTeamDni...");
        List<Project> projects = projectRepository.findByTeam_Dni(dni);
        return projects.stream().map(projectMapper::convertToDto).toList();
    }
}
