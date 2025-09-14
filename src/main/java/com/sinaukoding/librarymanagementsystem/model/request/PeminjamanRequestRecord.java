package com.sinaukoding.librarymanagementsystem.model.request;

import com.sinaukoding.librarymanagementsystem.model.enums.Kategori;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PeminjamanRequestRecord(String id,
                                      @NotNull(message = "dueDate tidak boleh kosong")
                                      @FutureOrPresent(message = "dueDate tidak boleh di masa lalu")
                                      LocalDateTime dueDate,
                                      @FutureOrPresent(message = "returnDate tidak boleh di masa lalu")
                                      LocalDateTime returnDate,
                                      @NotBlank(message = "buku id tidak boleh kosong")
                                      String bukuId) {
}
