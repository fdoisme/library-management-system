package com.sinaukoding.librarymanagementsystem.mapper.master;

import com.sinaukoding.librarymanagementsystem.entity.master.Lokasi;
import com.sinaukoding.librarymanagementsystem.model.request.LokasiRequestRecord;
import org.springframework.stereotype.Component;

@Component
public class LokasiMapper {

    public Lokasi requestToEntity(LokasiRequestRecord request) {
        String kodeLokasi = request.kodeLokasiAngka() + request.kodeLokasiAlphabet();
        return Lokasi.builder()
                .kodeLokasi(kodeLokasi)
                .kapasitas(request.kapasitas())
                .kapasitasTersedia(request.kapasitasTersedia())
                .build();
    }

}