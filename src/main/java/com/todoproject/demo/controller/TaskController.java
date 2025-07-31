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

import java.util.Map;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{projectCode}/tasks")
    public ResponseEntity<TaskResponseDto> createTask(
            @PathVariable String projectCode,
            @RequestBody TaskRequestDto taskRequestDto
    ) {
        // Estado por defecto: TODO
        taskRequestDto.setState("TODO");
        TaskResponseDto newTask = taskService.create(taskRequestDto, projectCode);
        return ResponseEntity.ok(newTask);
    }

    @PutMapping("/{taskCode}/state")
    public ResponseEntity<?> updateTaskState(
            @PathVariable String taskCode,
            @RequestBody Map<String, String> request
    ) {
        try {
            String newState = request.get("state");
            taskService.changeTaskState(taskCode, TaskState.valueOf(newState));
            taskService.changeTaskState(taskCode, TaskState.valueOf(newState));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error actualizando estado"));
        }
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
    @PutMapping("/{taskCode}/disable")
    public ResponseEntity<?> disableTaskByCode(@PathVariable String taskCode) {
        try {
            taskService.disableTaskByCode(taskCode);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error desactivando tarea"));
        }
    }

    @PatchMapping("/{code}/state")
    public ResponseEntity<Void> changeTaskState(
            @PathVariable String code,
            @RequestParam TaskState newState) {

        logger.info("PATCH /tasks/{}/state - Changing state to {}", code, newState);
        taskService.changeTaskState(code, newState);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable String code) {
        logger.info("GET /tasks/{} - Fetching task", code);
        TaskResponseDto task = taskService.findOne(code);
        return ResponseEntity.ok(task);
    }
}
