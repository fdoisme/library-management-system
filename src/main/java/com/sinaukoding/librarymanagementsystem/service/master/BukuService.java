package com.sinaukoding.librarymanagementsystem.service.master;

import com.sinaukoding.librarymanagementsystem.model.app.SimpleMap;
import com.sinaukoding.librarymanagementsystem.model.filter.BukuFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.BukuRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BukuService {

    void create(BukuRequestRecord request);

    void edit(BukuRequestRecord request);

    void delete(String id);

    Page<SimpleMap> findAll(BukuFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);

}
