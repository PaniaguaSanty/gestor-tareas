package com.todoproject.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentRequestDto {

    private String fileName;
    private String fileUrl;
    private LocalDateTime uploadedAt;
    //task request
}
