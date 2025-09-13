package com.sinaukoding.librarymanagementsystem.mapper.master;

import com.sinaukoding.librarymanagementsystem.entity.master.Buku;
import com.sinaukoding.librarymanagementsystem.model.request.BukuRequestRecord;
import org.springframework.stereotype.Component;

@Component
public class BukuMapper {

    public Buku requestToEntity(BukuRequestRecord request) {
        return Buku.builder()
                .judul(request.judul())
                .deskripsi(request.deskripsi())
                .pengarang(request.pengarang())
                .penerbit(request.penerbit())
                .tahunPublikasi(request.tahunPublikasi())
                .sampulBukuUrl(request.sampulBukuUrl())
                .kategori(request.kategori())
                .tags(request.tags())
//                .lokasi(request.lokasiId())
                .build();
    }

}