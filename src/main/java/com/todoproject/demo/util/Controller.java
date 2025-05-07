package com.todoproject.demo.util;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Controller<R, Q> {

    ResponseEntity<R> create(Q q);

    ResponseEntity<List<R>> findAll();

    ResponseEntity<R> findById(String id);

    ResponseEntity<R> update(Q q);

    ResponseEntity<Void> delete(String id);

}
