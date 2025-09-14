package com.sinaukoding.librarymanagementsystem.model.filter;

import com.sinaukoding.librarymanagementsystem.model.enums.Kategori;

import java.time.LocalDateTime;

public record PeminjamanFilterRecord(LocalDateTime dueDate,
                                     LocalDateTime returnDate,
                                     LocalDateTime createdDate,
                                     String bukuId,
                                     String userId) {
}
