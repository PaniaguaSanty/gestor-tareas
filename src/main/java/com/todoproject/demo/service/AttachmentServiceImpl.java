package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.AttachmentRequestDto;
import com.todoproject.demo.dto.response.AttachmentResponseDto;
import com.todoproject.demo.exception.NotFoundException;
import com.todoproject.demo.mapper.AttachmentMapper;
import com.todoproject.demo.model.Attachment;
import com.todoproject.demo.model.Task;
import com.todoproject.demo.repository.AttachmentRepository;
import com.todoproject.demo.repository.TaskRepository;
import com.todoproject.demo.util.CRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceImpl implements CRUD<AttachmentResponseDto, AttachmentRequestDto> {

    private final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final AttachmentMapper attachmentMapper;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository,
                                 TaskRepository taskRepository,
                                 AttachmentMapper attachmentMapper) {
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
        this.attachmentMapper = attachmentMapper;
    }

    @Override
    public AttachmentResponseDto create(AttachmentRequestDto attachmentRequestDto) {
        logger.info("Creating attachment...");

        //  pasar el ID de la tarea si esta en el DTO
        // Supongamos que lo agregamos:
        // private String taskCode;
        // Task task = taskRepository.findByCode(attachmentRequestDto.getTaskCode())
        //         .orElseThrow(() -> new NotFoundException("Task not found with code: " + attachmentRequestDto.getTaskCode()));

        Attachment attachment = new Attachment();
        attachment.setFileName(attachmentRequestDto.getFileName());
        attachment.setFileUrl(attachmentRequestDto.getFileUrl());
        attachment.setUploadedAt(LocalDateTime.now());
        attachment.setActive(true);
        // attachment.setTask(task);

        Attachment saved = attachmentRepository.save(attachment);
        return attachmentMapper.convertToDto(saved);
    }

    @Override
    public AttachmentResponseDto update(AttachmentRequestDto dto) {
        logger.info("Updating attachment...");
        Attachment attachment = attachmentRepository.findByFileName(dto.getFileName())
                .orElseThrow(() -> new NotFoundException("Attachment not found with filename: " + dto.getFileName()));

        attachment.setFileUrl(dto.getFileUrl());
        attachment.setUploadedAt(LocalDateTime.now());

        Attachment updated = attachmentRepository.save(attachment);
        return attachmentMapper.convertToDto(updated);
    }

    @Override
    public List<AttachmentResponseDto> findAll() {
        logger.info("Fetching all attachments...");
        return attachmentRepository.findAll().stream()
                .map(attachmentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AttachmentResponseDto findOne(String id) {
        logger.info("Fetching attachment by ID: {}", id);
        Long attachmentId = Long.parseLong(id);
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new NotFoundException("Attachment not found with ID: " + id));
        return attachmentMapper.convertToDto(attachment);
    }

    @Override
    public void delete(String id) {
        logger.info("Deleting attachment with ID: {}", id);
        Long attachmentId = Long.parseLong(id);
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new NotFoundException("Attachment not found with ID: " + id));
        attachmentRepository.delete(attachment);
    }

    @Override
    public AttachmentResponseDto disable(String id) {
        logger.info("Disabling attachment with ID: {}", id);
        Long attachmentId = Long.parseLong(id);
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new NotFoundException("Attachment not found with ID: " + id));

        attachment.setActive(false);
        attachmentRepository.save(attachment);
        return attachmentMapper.convertToDto(attachment);
    }
}