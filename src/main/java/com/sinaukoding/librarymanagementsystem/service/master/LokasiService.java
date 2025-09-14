package com.sinaukoding.librarymanagementsystem.service.master;

import com.sinaukoding.librarymanagementsystem.model.app.SimpleMap;
import com.sinaukoding.librarymanagementsystem.model.filter.LokasiFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.LokasiRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LokasiService {

    void create(LokasiRequestRecord request);

    void edit(LokasiRequestRecord request);

    void delete(String id);

    Page<SimpleMap> findAll(LokasiFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);

}