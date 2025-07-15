package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.TaskRequestDto;
import com.todoproject.demo.dto.response.TaskResponseDto;
import com.todoproject.demo.exception.NotFoundException;
import com.todoproject.demo.mapper.TaskMapper;
import com.todoproject.demo.model.Task;
import com.todoproject.demo.repository.TaskRepository;
import com.todoproject.demo.util.CRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
/*
    FALTA DEFINIR:
    Probablemente tenga que buscar el proyecto al cual le quiero setear esa tarea en específico, tambien buscar al
    usuario al cual se le asigna la tarea...
 */

public class TaskServiceImpl implements CRUD<TaskResponseDto, TaskRequestDto> {

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponseDto create(TaskRequestDto taskRequestDto) {
        logger.info("Entering in create Task method...");
        Task task = taskRepository.findByCode(taskRequestDto.getCode())
                .orElseThrow(() -> new NotFoundException("Task not found with code: " + taskRequestDto.getCode()));
        task.setActive(true);
        Task savedTask = taskRepository.save(task);
        return taskMapper.convertToDto(savedTask);
    }

    @Override
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

    @Override
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

    @Override
    public TaskResponseDto disable(String code) {
        logger.info("Entering in disable task method...");
        Task task = taskRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Task not found..."));
        task.setActive(false);
        taskRepository.save(task);
        return taskMapper.convertToDto(task);
    }

    @Override
    public TaskResponseDto findOne(String code) {
        logger.info("Entering in findOne task method...");
        Task task = taskRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Task not found..."));
        return taskMapper.convertToDto(task);
    }

    @Override
    public List<TaskResponseDto> findAll() {
        logger.info("Entering in findAll method...");
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::convertToDto)
                .collect(Collectors.toList());
    }

    //Método para cambiar el estado de la tarea...
    public void changeTaskState(String code) {
        logger.info("Entering in changeTaskState method...");
        Task task = taskRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Task not found..."));
        //TODO
    }
}
