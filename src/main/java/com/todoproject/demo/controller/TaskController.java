package com.todoproject.demo.controller;

import com.todoproject.demo.dto.request.TaskRequestDto;
import com.todoproject.demo.dto.response.TaskResponseDto;
import com.todoproject.demo.model.TaskState;
import com.todoproject.demo.service.TaskServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/project/{projectCode}")
    public ResponseEntity<TaskResponseDto> createTask(
            @PathVariable String projectCode,
            @RequestBody TaskRequestDto taskRequestDto) {

        logger.info("POST /tasks/project/{} - Creating task", projectCode);
        TaskResponseDto response = taskService.create(taskRequestDto, projectCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable String code) {
        logger.info("GET /tasks/{} - Fetching task", code);
        TaskResponseDto task = taskService.findOne(code);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{code}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable String code,
            @RequestBody TaskRequestDto taskRequestDto) {

        logger.info("PUT /tasks/{} - Updating task", code);
        // que el código de la ruta y del body coincidan o asigna el código de la ruta al DTO
        taskRequestDto.setCode(code);
        TaskResponseDto updatedTask = taskService.update(taskRequestDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteTask(@PathVariable String code) {
        logger.info("DELETE /tasks/{} - Deleting task", code);
        taskService.delete(code);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disableTaskById(@PathVariable Long id) {
        logger.info("PUT /tasks/disable/{} - Disabling task", id);
        taskService.disableById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{code}/state")
    public ResponseEntity<Void> changeTaskState(
            @PathVariable String code,
            @RequestParam TaskState newState) {

        logger.info("PATCH /tasks/{}/state - Changing state to {}", code, newState);
        taskService.changeTaskState(code, newState);
        return ResponseEntity.noContent().build();
    }
}
