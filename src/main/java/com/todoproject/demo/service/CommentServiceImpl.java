package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.CommentRequestDto;
import com.todoproject.demo.dto.response.CommentResponseDto;
import com.todoproject.demo.exception.NotFoundException;
import com.todoproject.demo.mapper.CommentMapper;
import com.todoproject.demo.model.Comment;
import com.todoproject.demo.model.Task;
import com.todoproject.demo.model.User;
import com.todoproject.demo.repository.CommentRepository;
import com.todoproject.demo.repository.TaskRepository;
import com.todoproject.demo.repository.UserRepository;
import com.todoproject.demo.util.CRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CRUD<CommentResponseDto, CommentRequestDto> {

    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              TaskRepository taskRepository,
                              UserRepository userRepository,
                              CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentResponseDto create(CommentRequestDto dto) {
        logger.info("Creating new comment...");

        Task task = taskRepository.findByCode(dto.getTaskCode())
                .orElseThrow(() -> new NotFoundException("Task not found with code: " + dto.getTaskCode()));

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + dto.getAuthorId()));

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(author);
        comment.setTask(task);

        Comment saved = commentRepository.save(comment);
        return commentMapper.convertToDto(saved);
    }

    @Override
    public CommentResponseDto update(CommentRequestDto dto) {
        logger.info("Updating comment...");
        Long commentId = dto.getId(); // asumimos que viene en el DTO
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with ID: " + commentId));

        comment.setContent(dto.getContent());
        comment.setCreatedAt(LocalDateTime.now()); // Actualizamos fecha

        Comment updated = commentRepository.save(comment);
        return commentMapper.convertToDto(updated);
    }

    @Override
    public List<CommentResponseDto> findAll() {
        logger.info("Fetching all comments...");
        return commentRepository.findAll().stream()
                .map(commentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto findOne(String id) {
        logger.info("Fetching comment by ID: {}", id);
        Long commentId = Long.parseLong(id);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with ID: " + id));
        return commentMapper.convertToDto(comment);
    }

    @Override
    public void delete(String id) {
        logger.info("Deleting comment with ID: {}", id);
        Long commentId = Long.parseLong(id);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with ID: " + id));
        commentRepository.delete(comment);
    }

    @Override
    public CommentResponseDto disable(String id) {
        logger.warn("Disable operation not supported for Comment.");
        throw new UnsupportedOperationException("Comments cannot be disabled. Use delete instead.");
    }
}
