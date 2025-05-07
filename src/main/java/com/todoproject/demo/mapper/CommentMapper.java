package com.todoproject.demo.mapper;

import com.todoproject.demo.dto.request.CommentRequestDto;
import com.todoproject.demo.dto.response.CommentResponseDto;
import com.todoproject.demo.model.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {

    private final ModelMapper modelMapper;

    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommentResponseDto convertToDto(Comment comment) {
        return modelMapper.map(comment, CommentResponseDto.class);
    }

    public Comment convertToEntity(CommentRequestDto commentRequestDto) {
        return modelMapper.map(commentRequestDto, Comment.class);
    }

}
