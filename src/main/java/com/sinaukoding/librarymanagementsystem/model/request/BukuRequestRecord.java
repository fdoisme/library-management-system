package com.sinaukoding.librarymanagementsystem.model.request;

import com.sinaukoding.librarymanagementsystem.model.enums.Kategori;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BukuRequestRecord(String id,
                                @NotBlank(message = "judul tidak boleh kosong")
                                String judul,
                                @NotBlank(message = "deskripsi tidak boleh kosong")
                                String deskripsi,
                                @NotBlank(message = "pengarang tidak boleh kosong")
                                String pengarang,
                                @NotBlank(message = "penerbit tidak boleh kosong")
                                String penerbit,
                                @NotNull(message = "tahunPublikasi tidak boleh kosong")
                                Integer tahunPublikasi,
                                String sampulBukuUrl,
                                @NotNull(message = "kategori tidak boleh kosong")
                                Kategori kategori,
                                @NotBlank(message = "tags tidak boleh kosong")
                                String tags,
                                String lokasiId) {
}
