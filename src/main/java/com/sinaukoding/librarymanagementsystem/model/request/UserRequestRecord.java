package com.sinaukoding.librarymanagementsystem.model.request;

import com.sinaukoding.librarymanagementsystem.model.enums.Role;
import com.sinaukoding.librarymanagementsystem.model.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestRecord(String id,
                                @NotBlank(message = "nama tidak boleh kosong")
                                String nama,
                                @NotBlank(message = "username tidak boleh kosong")
                                String username,
                                @NotBlank(message = "email tidak boleh kosong")
                                @Email(message = "email tidak valid")
                                String email,
                                @NotBlank(message = "password tidak boleh kosong")
                                String password) {
}