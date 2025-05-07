package com.todoproject.demo.dto.response;

import com.todoproject.demo.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponseDto {

    private String fileName;
    private String fileUrl;
    private LocalDateTime uploadedAt;
    private Task task;

}
