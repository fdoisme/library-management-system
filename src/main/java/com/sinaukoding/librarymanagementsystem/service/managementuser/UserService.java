package com.sinaukoding.librarymanagementsystem.service.managementuser;

import com.sinaukoding.librarymanagementsystem.model.app.SimpleMap;
import com.sinaukoding.librarymanagementsystem.model.enums.Role;
import com.sinaukoding.librarymanagementsystem.model.filter.UserFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.UserRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void register(UserRequestRecord request);

    void register(UserRequestRecord request, Role role);

    void edit(UserRequestRecord request);

    void delete(String id);

    Page<SimpleMap> findAll(UserFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);

}