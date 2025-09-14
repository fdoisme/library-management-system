package com.sinaukoding.librarymanagementsystem.service.master.impl;

import com.sinaukoding.librarymanagementsystem.builder.CustomBuilder;
import com.sinaukoding.librarymanagementsystem.entity.managementuser.User;
import com.sinaukoding.librarymanagementsystem.entity.master.Buku;
import com.sinaukoding.librarymanagementsystem.entity.master.Lokasi;
import com.sinaukoding.librarymanagementsystem.entity.master.Peminjaman;
import com.sinaukoding.librarymanagementsystem.mapper.master.PeminjamanMapper;
import com.sinaukoding.librarymanagementsystem.model.app.AppPage;
import com.sinaukoding.librarymanagementsystem.model.app.SimpleMap;
import com.sinaukoding.librarymanagementsystem.model.filter.PeminjamanFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.BukuRequestRecord;
import com.sinaukoding.librarymanagementsystem.model.request.PeminjamanRequestRecord;
import com.sinaukoding.librarymanagementsystem.repository.managementuser.UserRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.BukuRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.LokasiRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.PeminjamanRepository;
import com.sinaukoding.librarymanagementsystem.service.app.ValidatorService;
import com.sinaukoding.librarymanagementsystem.service.master.PeminjamanService;
import com.sinaukoding.librarymanagementsystem.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PeminjamanServiceImpl implements PeminjamanService {
    private final PeminjamanRepository peminjamanRepository;
    private final UserRepository userRepository;
    private final BukuRepository bukuRepository;
    private final LokasiRepository lokasiRepository;
    private final PeminjamanMapper peminjamanMapper;
    private final ValidatorService validatorService;

    @Override
    public void create(PeminjamanRequestRecord request) {
        validatorService.validator(request);
//        Cek apakah user valid
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("username not found"));
//        Cek apakah buku id dari request valid
        Buku buku = bukuRepository.findById(request.bukuId()).orElseThrow(() -> new RuntimeException("buku not found"));
//        Cek apakah user lagi dalam peminjaman buku (karena hanya boleh 1 buku untuk 1 user)
        if(peminjamanRepository.existsByUserIdAndReturnDateIsNull(user.getId())){
            throw new RuntimeException("User sedang dalam masa pinjam");
        }
//        Ketika id buku valid cek apakah buku sedang dipinjam
        if (buku.getLokasi() == null) {
            throw new RuntimeException("Buku sedang dipinjam");
        }
        Peminjaman peminjaman = peminjamanMapper.requestToEntity(request);
        peminjaman.setReturnDate(null);
        peminjaman.setUser(user);
        peminjaman.setBuku(buku);
        peminjamanRepository.save(peminjaman);
        Lokasi lokasi = buku.getLokasi();
        lokasi.setKapasitasTersedia(lokasi.getKapasitasTersedia() + 1);
        lokasiRepository.save(lokasi);
        buku.setLokasi(null);
        bukuRepository.save(buku);
    }

    @Override
    public void edit(PeminjamanRequestRecord request) {
        validatorService.validator(request);
        var peminjaman = peminjamanRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("peminjaman not found"));
        if (peminjaman.getReturnDate() != null) {
            throw new RuntimeException("buku sudah dikembalikan");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("username not found"));
        if (!peminjaman.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("id peminjaman tidak sesuai dengan id user");
        }
        peminjaman.setReturnDate(LocalDateTime.now());
        System.out.println("peminjaman return date: " + peminjaman.getReturnDate());
        peminjamanRepository.save(peminjaman);
//        Cek apakah ada lokasi yang masih tersedia untuk pengembalian buku
        Lokasi lokasi = lokasiRepository.findByKapasitasTersediaGreaterThan(0).stream().findFirst().orElseThrow(()-> new RuntimeException("Lokasi tidak ditemukan"));
        lokasi.setKapasitasTersedia(lokasi.getKapasitasTersedia()-1);
        lokasiRepository.save(lokasi);
        Buku buku = bukuRepository.findById(peminjaman.getBuku().getId()).orElseThrow(() -> new RuntimeException("buku not found"));
        buku.setLokasi(lokasi);
        bukuRepository.save(buku);
    }

    @Override
    public void delete(String id) {
        var user = peminjamanRepository.findById(id).orElseThrow(() -> new RuntimeException("Data peminjaman tidak ditemukan"));
        peminjamanRepository.delete(user);
    }

    @Override
    public Page<SimpleMap> findAll(PeminjamanFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<Peminjaman> builder = new CustomBuilder<>();
        FilterUtil.builderConditionNotNullEqual("dueDate", filterRequest.dueDate(), builder);
        FilterUtil.builderConditionNotNullEqual("returnDate", filterRequest.returnDate(), builder);
        FilterUtil.builderConditionNotNullEqual("createdDate", filterRequest.createdDate(), builder);
        FilterUtil.builderConditionNotBlankLike("buku.id", filterRequest.bukuId(),  builder);
        FilterUtil.builderConditionNotBlankLike("user.id", filterRequest.userId(),  builder);

        Page<Peminjaman> listPeminjaman = peminjamanRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = listPeminjaman.stream().map(peminjaman -> {
            SimpleMap data = new SimpleMap();
            data.put("id", peminjaman.getId());
            data.put("startDate", peminjaman.getCreatedDate());
            data.put("dueDate", peminjaman.getDueDate());
            data.put("returnDate", peminjaman.getReturnDate());
            data.put("buku", peminjaman.getBuku().getId());
            return data;
        }).toList();
        return AppPage.create(listData, pageable, listPeminjaman.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var peminjaman = peminjamanRepository.findById(id).orElseThrow(() -> new RuntimeException("Data peminjaman tidak ditemukan"));
        SimpleMap data = new SimpleMap();
        data.put("startDate", peminjaman.getCreatedDate());
        data.put("dueDate", peminjaman.getDueDate());
        data.put("returnDate", peminjaman.getReturnDate());
        data.put("buku", peminjaman.getBuku().getId());
        return data;
    }
}
