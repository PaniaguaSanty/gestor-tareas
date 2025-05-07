package com.todoproject.demo.util;

import java.util.List;

public interface CRUD<R, Q> {

    R create(Q q);

    R update(Q q);

    List<R> findAll();

    R findOne(String id);

    void delete(String id);

    R disable(String id);

}
