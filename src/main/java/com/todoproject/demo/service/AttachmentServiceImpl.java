package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.AttachmentRequestDto;
import com.todoproject.demo.dto.response.AttachmentResponseDto;
import com.todoproject.demo.util.CRUD;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentServiceImpl implements CRUD<AttachmentResponseDto, AttachmentRequestDto> {
    @Override
    public AttachmentResponseDto create(AttachmentRequestDto attachmentRequestDto) {
        return null;
    }

    @Override
    public AttachmentResponseDto update(AttachmentRequestDto attachmentRequestDto) {
        return null;
    }

    @Override
    public List<AttachmentResponseDto> findAll() {
        return List.of();
    }

    @Override
    public AttachmentResponseDto findOne(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public AttachmentResponseDto disable(String id) {
        return null;
    }
}
