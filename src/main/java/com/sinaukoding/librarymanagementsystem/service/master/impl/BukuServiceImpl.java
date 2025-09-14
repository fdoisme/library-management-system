package com.sinaukoding.librarymanagementsystem.service.master.impl;

import com.sinaukoding.librarymanagementsystem.builder.CustomBuilder;
import com.sinaukoding.librarymanagementsystem.entity.managementuser.User;
import com.sinaukoding.librarymanagementsystem.entity.master.Buku;
import com.sinaukoding.librarymanagementsystem.entity.master.Lokasi;
import com.sinaukoding.librarymanagementsystem.mapper.master.BukuMapper;
import com.sinaukoding.librarymanagementsystem.model.app.AppPage;
import com.sinaukoding.librarymanagementsystem.model.app.SimpleMap;
import com.sinaukoding.librarymanagementsystem.model.filter.BukuFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.BukuRequestRecord;
import com.sinaukoding.librarymanagementsystem.repository.master.BukuRepository;
import com.sinaukoding.librarymanagementsystem.repository.master.LokasiRepository;
import com.sinaukoding.librarymanagementsystem.service.app.ValidatorService;
import com.sinaukoding.librarymanagementsystem.service.master.BukuService;
import com.sinaukoding.librarymanagementsystem.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BukuServiceImpl implements BukuService {
    private final BukuRepository bukuRepository;
    private final ValidatorService validatorService;
    private final BukuMapper bukuMapper;
    private final LokasiRepository lokasiRepository;

    @Override
    public void create(BukuRequestRecord request) {
        validatorService.validator(request);
        var lokasi = lokasiRepository.findByIdAndKapasitasTersediaGreaterThan(request.lokasiId(), 0);
        if (lokasi.isPresent()) {
            Lokasi lokasiData = lokasi.get();
            lokasiData.setKapasitasTersedia(lokasiData.getKapasitas() - 1);
            lokasiRepository.saveAndFlush(lokasiData);
            var buku = bukuMapper.requestToEntity(request);
            buku.setLokasi(lokasiData);
            bukuRepository.save(buku);
        } else {
            throw new RuntimeException("Lokasi tidak ditemukan");
        }
    }

    @Override
    public void edit(BukuRequestRecord request) {
        validatorService.validator(request);
        var bukuExisting = bukuRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("Data buku tidak ditemukan"));

//        Kalau lokasi nya berbeda maka harus dicek ketersediaan, mengurangi kapasitas tujuan dan menambahkan kapasitas asal
        if (!bukuExisting.getLokasi().getId().equals(request.lokasiId())) {
            var lokasi = lokasiRepository.findByIdAndKapasitasTersediaGreaterThan(request.lokasiId(), 0);
            if (lokasi.isPresent()) {
                Lokasi lokasiData = lokasi.get();
                System.out.println("Lokasi Data : "+lokasiData.getKapasitasTersedia());
                System.out.println("Lokasi Existing : "+bukuExisting.getLokasi().getKapasitasTersedia());
                lokasiData.setKapasitasTersedia(lokasiData.getKapasitasTersedia() - 1);
                lokasiRepository.saveAndFlush(lokasiData);
                bukuExisting.getLokasi().setKapasitasTersedia(bukuExisting.getLokasi().getKapasitasTersedia() + 1);
                lokasiRepository.saveAndFlush(bukuExisting.getLokasi());
                var buku = bukuMapper.requestToEntity(request);
                buku.setLokasi(lokasiData);
                buku.setId(request.id());
                bukuRepository.save(buku);
            } else {
                throw new RuntimeException("Lokasi tidak ditemukan");
            }
        }
    }

    @Override
    public void delete(String id) {
        var user = bukuRepository.findById(id).orElseThrow(() -> new RuntimeException("Data buku tidak ditemukan"));
        bukuRepository.delete(user);
    }

    @Override
    public Page<SimpleMap> findAll(BukuFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<Buku> builder = new CustomBuilder<>();
        FilterUtil.builderConditionNotBlankLike("judul", filterRequest.judul(), builder);
        FilterUtil.builderConditionNotBlankLike("pengarang", filterRequest.pengarang(), builder);
        FilterUtil.builderConditionNotBlankLike("penerbit", filterRequest.penerbit(), builder);
        FilterUtil.builderConditionNotNullEqual("tahunPublikasi", filterRequest.tahunPublikasi(), builder);
        FilterUtil.builderConditionNotNullEqual("kategori", filterRequest.kategori(), builder);
        FilterUtil.builderConditionNotBlankLike("tags", filterRequest.tags(), builder);

        Page<Buku> listBuku = bukuRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = listBuku.stream().map(buku -> {
            SimpleMap data = new SimpleMap();
            data.put("id", buku.getId());
            data.put("judul", buku.getJudul());
            data.put("deskripsi", buku.getDeskripsi());
            data.put("pengarang", buku.getPengarang());
            data.put("penerbit", buku.getPenerbit());
            data.put("tahunPublikasi", buku.getTahunPublikasi());
            data.put("sampulBukuUrl", buku.getSampulBukuUrl());
            data.put("kategori", buku.getKategori().getLabel());
            data.put("tags", buku.getTags());
            data.put("lokasi", buku.getLokasi().getKodeLokasi());
            return data;
        }).toList();
        return AppPage.create(listData, pageable, listBuku.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var buku = bukuRepository.findById(id).orElseThrow(() -> new RuntimeException("Data buku tidak ditemukan"));
        SimpleMap data = new SimpleMap();
        data.put("judul", buku.getJudul());
        data.put("deskripsi", buku.getDeskripsi());
        data.put("pengarang", buku.getPengarang());
        data.put("penerbit", buku.getPenerbit());
        data.put("tahunPublikasi", buku.getTahunPublikasi());
        data.put("sampulBukuUrl", buku.getSampulBukuUrl());
        data.put("kategori", buku.getKategori().getLabel());
        data.put("tags", buku.getTags());
        data.put("lokasi", buku.getLokasi().getKodeLokasi());
        return data;
    }
}
