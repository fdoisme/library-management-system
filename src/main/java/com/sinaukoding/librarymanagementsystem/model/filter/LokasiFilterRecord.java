package com.sinaukoding.librarymanagementsystem.model.filter;

import com.sinaukoding.librarymanagementsystem.model.enums.Role;
import com.sinaukoding.librarymanagementsystem.model.enums.Status;

public record LokasiFilterRecord(String kodeLokasi,
                                 Integer kapasitas,
                                 Integer kapasitasTersedia) {
}
