package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.CommentRequestDto;
import com.todoproject.demo.dto.response.CommentResponseDto;
import com.todoproject.demo.util.CRUD;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CRUD<CommentResponseDto, CommentRequestDto> {

    @Override
    public CommentResponseDto create(CommentRequestDto commentRequestDto) {
        return null;
    }

    @Override
    public CommentResponseDto update(CommentRequestDto commentRequestDto) {
        return null;
    }

    @Override
    public List<CommentResponseDto> findAll() {
        return List.of();
    }

    @Override
    public CommentResponseDto findOne(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public CommentResponseDto disable(String id) {
        return null;
    }
}
