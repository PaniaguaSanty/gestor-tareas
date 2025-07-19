package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.TaskRequestDto;
import com.todoproject.demo.dto.response.TaskResponseDto;
import com.todoproject.demo.exception.NotFoundException;
import com.todoproject.demo.mapper.TaskMapper;
import com.todoproject.demo.model.Project;
import com.todoproject.demo.model.Task;
import com.todoproject.demo.model.TaskState;
import com.todoproject.demo.model.User;
import com.todoproject.demo.repository.ProjectRepository;
import com.todoproject.demo.repository.TaskRepository;
import com.todoproject.demo.repository.UserRepository;
import com.todoproject.demo.util.CRUD;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;


    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponseDto create(TaskRequestDto taskRequestDto, String projectCode) {
        logger.info("Entering in create Task method...");
        Project project = findProjectByCode(projectCode);

        User user = findUserIfPresent(taskRequestDto.getUserId());

        Task task = buildTask(taskRequestDto, project, user);
        Task savedTask = taskRepository.save(task);

        return taskMapper.convertToDto(savedTask);
    }

    private Project findProjectByCode(String code) {
        return projectRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Project not found with code: " + code));
    }

    private User findUserIfPresent(Long userId) {
        if (userId == null) return null;
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
    }

    private Task buildTask(TaskRequestDto dto, Project project, User user) {
        Task task = taskMapper.convertToEntity(dto);
        task.setId(null);  // <-- esto es clave para evitar el error de versión y que JPA entienda que es nueva
        task.setProject(project);
        task.setAssignedTo(user); // null if no user
        task.setActive(true);
        return task;
    }


    public TaskResponseDto update(TaskRequestDto taskRequestDto) {
        logger.info("Entering in update Task method...");
        try {
            Task existingTask = taskRepository.findByCode(taskRequestDto.getCode())
                    .orElseThrow(() -> new NotFoundException("Task not found with code: " + taskRequestDto.getCode()));
            //existingTask.setComments(taskRequestDto.getComments());
            //existingTask.setAttachments(taskRequestDto.getAttachments());
            existingTask.setDescription(taskRequestDto.getDescription());
            existingTask.setDescription(taskRequestDto.getDescription());
            Task savedTask = taskRepository.save(existingTask);
            return taskMapper.convertToDto(savedTask);
        } catch (NotFoundException e) {
            throw new NotFoundException("Error while updating tasks...");
        }
    }

    public void delete(String code) {
        Task task = taskRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Task not found..."));
        taskRepository.delete(task);
    }

    public void enable(String code) {
        logger.info("Entering in enable task method...");
        Task task = taskRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Task not found..."));
        task.setActive(true);
        taskRepository.save(task);
        taskMapper.convertToDto(task);
    }

    public TaskResponseDto disable(String code) {
        logger.info("Entering in disable task method...");
        Task task = taskRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Task not found..."));
        task.setActive(false);
        taskRepository.save(task);
        return taskMapper.convertToDto(task);
    }


    public TaskResponseDto findOne(String code) {
        logger.info("Entering in findOne task method...");
        Task task = taskRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Task not found..."));
        return taskMapper.convertToDto(task);
    }


    public List<TaskResponseDto> findByProjectCode(String code) {
        List<Task> tasks = taskRepository.findByProject_Code(code);
        return tasks.stream()
                .map(taskMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> findAll() {
        logger.info("Entering in findAll method...");
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public void changeTaskState(String code, TaskState newState) {
        Task task = taskRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Task not found with code: " + code));
        task.setState(newState);
        taskRepository.save(task);
    }

    @Transactional
    public void disableById(Long id) {
        logger.info("Disabling task by ID: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + id));

        task.setActive(false);
        taskRepository.save(task);
    }

    public List<TaskResponseDto> findActiveByProjectCode(String projectCode) {
        Project project = projectRepository.findByCode(projectCode)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with code: " + projectCode));

        List<Task> tasks = taskRepository.findByProjectIdAndActiveTrue(project.getId());
        return tasks.stream()
                .map(taskMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
