package com.sinaukoding.librarymanagementsystem;

import com.sinaukoding.librarymanagementsystem.entity.managementuser.User;
import com.sinaukoding.librarymanagementsystem.entity.master.Buku;
import com.sinaukoding.librarymanagementsystem.entity.master.Lokasi;
import com.sinaukoding.librarymanagementsystem.entity.master.Peminjaman;
import com.sinaukoding.librarymanagementsystem.mapper.master.BukuMapper;
import com.sinaukoding.librarymanagementsystem.mapper.master.PeminjamanMapper;
import com.sinaukoding.librarymanagementsystem.model.enums.Kategori;
import com.sinaukoding.librarymanagementsystem.model.request.BukuRequestRecord;
import com.sinaukoding.librarymanagementsystem.model.request.PeminjamanRequestRecord;
import com.sinaukoding.librarymanagementsystem.repository.managementuser.UserRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.BukuRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.LokasiRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.PeminjamanRepository;
import com.sinaukoding.librarymanagementsystem.service.app.ValidatorService;
import com.sinaukoding.librarymanagementsystem.service.master.impl.BukuServiceImpl;
import com.sinaukoding.librarymanagementsystem.service.master.impl.PeminjamanServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeminjamanServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PeminjamanRepository peminjamanRepository;

    @Mock
    private BukuRepository bukuRepository;

    @Mock
    private LokasiRepository lokasiRepository;

    @Mock
    private ValidatorService validatorService;

    @Mock
    private PeminjamanMapper peminjamanMapper;

    @InjectMocks
    private PeminjamanServiceImpl peminjamanService;

    @Test
    void testAddPeminjaman_Success() {
        SecurityContext context = mock(SecurityContext.class);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("username");
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        LocalDateTime now = LocalDateTime.now().plusDays(5);
        var request = new PeminjamanRequestRecord(null, now, null, "bukuId");
        User user = new User();
        user.setId("userId");
        user.setUsername("username");

        Buku buku = new Buku();
        Lokasi lokasi = new Lokasi();
        lokasi.setKapasitasTersedia(1);
        buku.setLokasi(lokasi);

        Peminjaman peminjaman = new Peminjaman();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(bukuRepository.findById("bukuId")).thenReturn(Optional.of(buku));
        when(peminjamanRepository.existsByUserIdAndReturnDateIsNull("userId")).thenReturn(false);
        when(peminjamanMapper.requestToEntity(request)).thenReturn(peminjaman);

        peminjamanService.create(request);
        verify(validatorService, times(1)).validator(request);
        verify(bukuRepository, times(1)).save(buku);
        verify(peminjamanRepository, times(1)).save(peminjaman);
        SecurityContextHolder.clearContext();
    }
}
