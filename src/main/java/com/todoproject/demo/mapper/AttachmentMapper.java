package com.todoproject.demo.mapper;

import com.todoproject.demo.dto.request.AttachmentRequestDto;
import com.todoproject.demo.dto.response.AttachmentResponseDto;
import com.todoproject.demo.model.Attachment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AttachmentMapper {

    private final ModelMapper modelMapper;

    public AttachmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AttachmentResponseDto convertToDto(Attachment attachment) {
        return modelMapper.map(attachment, AttachmentResponseDto.class);
    }

    public Attachment convertToEntity(AttachmentRequestDto attachmentRequestDto) {
        return modelMapper.map(attachmentRequestDto, Attachment.class);
    }
}
