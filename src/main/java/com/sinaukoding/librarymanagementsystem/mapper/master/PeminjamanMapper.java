package com.sinaukoding.librarymanagementsystem.mapper.master;

import com.sinaukoding.librarymanagementsystem.entity.master.Peminjaman;
import com.sinaukoding.librarymanagementsystem.model.request.PeminjamanRequestRecord;
import org.springframework.stereotype.Component;

@Component
public class PeminjamanMapper {
    public Peminjaman requestToEntity(PeminjamanRequestRecord request) {
        return Peminjaman.builder()
                .dueDate(request.dueDate())
                .returnDate(request.returnDate())
                .build();
    }
}
