package com.sinaukoding.librarymanagementsystem;

import com.sinaukoding.librarymanagementsystem.entity.master.Buku;
import com.sinaukoding.librarymanagementsystem.entity.master.Lokasi;
import com.sinaukoding.librarymanagementsystem.mapper.master.BukuMapper;
import com.sinaukoding.librarymanagementsystem.mapper.master.LokasiMapper;
import com.sinaukoding.librarymanagementsystem.model.enums.Kategori;
import com.sinaukoding.librarymanagementsystem.model.request.BukuRequestRecord;
import com.sinaukoding.librarymanagementsystem.model.request.LokasiRequestRecord;
import com.sinaukoding.librarymanagementsystem.repository.managementuser.UserRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.BukuRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.LokasiRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.PeminjamanRepository;
import com.sinaukoding.librarymanagementsystem.service.app.ValidatorService;
import com.sinaukoding.librarymanagementsystem.service.master.impl.BukuServiceImpl;
import com.sinaukoding.librarymanagementsystem.service.master.impl.LokasiServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LokasiServiceTest {

    @Mock
    private LokasiRepository lokasiRepository;

    @Mock
    private ValidatorService validatorService;

    @Mock
    private LokasiMapper lokasiMapper;

    @InjectMocks
    private LokasiServiceImpl lokasiService;

    @Test
    void testAddLokasi_Success() {
        var request = new LokasiRequestRecord(null, 2, "F", 8, 7);
        Lokasi lokasi = new Lokasi();
        lokasi.setId("lokasiId");
        when(lokasiMapper.requestToEntity(request)).thenReturn(lokasi);

        lokasiService.create(request);

        verify(validatorService, times(1)).validator(request);
        verify(lokasiRepository, times(1)).save(lokasi);
    }
}
