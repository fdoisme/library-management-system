package com.sinaukoding.librarymanagementsystem;

import com.sinaukoding.librarymanagementsystem.entity.master.Buku;
import com.sinaukoding.librarymanagementsystem.entity.master.Lokasi;
import com.sinaukoding.librarymanagementsystem.mapper.master.BukuMapper;
import com.sinaukoding.librarymanagementsystem.model.enums.Kategori;
import com.sinaukoding.librarymanagementsystem.model.request.BukuRequestRecord;
import com.sinaukoding.librarymanagementsystem.repository.master.BukuRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.LokasiRepository;
import com.sinaukoding.librarymanagementsystem.service.app.ValidatorService;
import com.sinaukoding.librarymanagementsystem.service.master.impl.BukuServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BukuServiceTest {

    @Mock
    private BukuRepository bukuRepository;

    @Mock
    private LokasiRepository lokasiRepository;

    @Mock
    private ValidatorService validatorService;

    @Mock
    private BukuMapper bukuMapper;

    @InjectMocks
    private BukuServiceImpl bukuService;

    @Test
    void testAddBuku_Success() {

        var request = new BukuRequestRecord(null, "Belajar Programming", "Sebuah buku untuk melatih berpikir layaknya programmer", "Anonymous", "Anonymous", 1990, "", Kategori.BUKU_PELAJARAN, "Teknologi, Komputer, Sains", "lokasiId");

        var buku = new Buku();
        when(bukuMapper.requestToEntity(request)).thenReturn(buku);
        Lokasi lokasi = new Lokasi();
        lokasi.setId("2F");
        lokasi.setKapasitas(10);
        lokasi.setKapasitasTersedia(5);

        when(lokasiRepository.findByIdAndKapasitasTersediaGreaterThan("lokasiId", 0))
                .thenReturn(Optional.of(lokasi));
        // when
        bukuService.create(request);

        // then
        verify(validatorService, times(1)).validator(request);
        verify(bukuRepository, times(1)).save(buku);
    }
}
