package com.sinaukoding.librarymanagementsystem.model.request;

import com.sinaukoding.librarymanagementsystem.model.enums.Kategori;
import jakarta.validation.constraints.*;

public record LokasiRequestRecord(String id,
                                  @NotNull(message = "kode lokasi angka tidak boleh kosong")
                                  @Min(value = 0, message = "kode lokasi angka tidak boleh kurang dari 0")
                                  Integer kodeLokasiAngka,
                                  @NotBlank(message = "kode lokasi alphabet tidak boleh kosong")
                                  @Pattern(regexp = "[A-F]", message = "kode lokasi alphabet hanya boleh huruf A sampai F")
                                  String kodeLokasiAlphabet,
                                  @NotNull(message = "kapasitas tidak boleh kosong")
                                  @Min(value = 1, message = "kapasitas harus lebih dari 0")
                                  Integer kapasitas,
                                  @NotNull(message = "kapasitas tersedia tidak boleh kosong")
                                  @Min(value = 0, message = "kapasitas tersedia tidak boleh kurang dari 0")
                                  Integer kapasitasTersedia){
}