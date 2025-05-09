package com.todoproject.demo.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudController<RequestDto, ResponseDto> {

    ResponseEntity<ResponseDto> create(RequestDto REQUEST);

    ResponseEntity<ResponseDto> update(Long id, RequestDto REQUEST);

    ResponseEntity<Void> delete(Long id);

    ResponseEntity<ResponseDto> findById(Long id);

    ResponseEntity<List<ResponseDto>> findAll();

}