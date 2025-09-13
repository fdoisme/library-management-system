package com.sinaukoding.librarymanagementsystem.model.filter;

import com.sinaukoding.librarymanagementsystem.model.enums.Kategori;

public record BukuFilterRecord(String judul,
                               String pengarang,
                               String penerbit,
                               Integer tahunPublikasi,
                               Kategori kategori,
                               String tags){
}
