package com.sinaukoding.librarymanagementsystem.service.master;

import com.sinaukoding.librarymanagementsystem.model.app.SimpleMap;
import com.sinaukoding.librarymanagementsystem.model.filter.PeminjamanFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.PeminjamanRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeminjamanService {

    void create(PeminjamanRequestRecord request);

    void edit(PeminjamanRequestRecord request);

    void delete(String id);

    Page<SimpleMap> findAll(PeminjamanFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);

}