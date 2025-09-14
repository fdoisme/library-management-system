package com.sinaukoding.librarymanagementsystem.service.master.impl;

import com.sinaukoding.librarymanagementsystem.builder.CustomBuilder;
import com.sinaukoding.librarymanagementsystem.entity.master.Lokasi;
import com.sinaukoding.librarymanagementsystem.mapper.master.LokasiMapper;
import com.sinaukoding.librarymanagementsystem.model.app.AppPage;
import com.sinaukoding.librarymanagementsystem.model.app.SimpleMap;
import com.sinaukoding.librarymanagementsystem.model.filter.LokasiFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.LokasiRequestRecord;
import com.sinaukoding.librarymanagementsystem.repository.master.LokasiRepository;
import com.sinaukoding.librarymanagementsystem.service.app.ValidatorService;
import com.sinaukoding.librarymanagementsystem.service.master.LokasiService;
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
public class LokasiServiceImpl implements LokasiService {
    private final ValidatorService validatorService;
    private final LokasiRepository lokasiRepository;
    private final LokasiMapper lokasiMapper;

    @Override
    public void create(LokasiRequestRecord request) {
        validatorService.validator(request);
        var lokasi = lokasiMapper.requestToEntity(request);
        if (lokasiRepository.existsByKodeLokasi(lokasi.getKodeLokasi())){
            throw new RuntimeException("Lokasi [" + lokasi.getKodeLokasi() + "] sudah terdaftar");
        }
        lokasiRepository.save(lokasi);
    }

    @Override
    public void edit(LokasiRequestRecord request) {
        validatorService.validator(request);
        var lokasiExisting = lokasiRepository.findById(request.id()).orElseThrow(()-> new RuntimeException("Data Lokasi tidak ditemukan"));
        var lokasi = lokasiMapper.requestToEntity(request);
        if (lokasiRepository.existsByKodeLokasi(lokasi.getKodeLokasi())){
            throw new RuntimeException("Lokasi [" + lokasi.getKodeLokasi() + "] sudah terdaftar");
        }
        lokasi.setId(lokasiExisting.getId());
        lokasiRepository.save(lokasi);
    }

    @Override
    public void delete(String id) {
        var lokasi = lokasiRepository.findById(id).orElseThrow(() -> new RuntimeException("Data lokasi tidak ditemukan"));
        lokasiRepository.delete(lokasi);
    }

    @Override
    public Page<SimpleMap> findAll(LokasiFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<Lokasi> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("kodeLokasi", filterRequest.kodeLokasi(), builder);
        FilterUtil.builderConditionNotNullEqual("kapasitas", filterRequest.kapasitas(), builder);
        FilterUtil.builderConditionNotNullEqual("kapasitasTersedia", filterRequest.kapasitasTersedia(), builder);

        Page<Lokasi> listLokasi = lokasiRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = listLokasi.stream().map(lokasi -> {
            SimpleMap data = new SimpleMap();
            data.put("id", lokasi.getId());
            data.put("kodeLokasi", lokasi.getKodeLokasi());
            data.put("kapasitas", lokasi.getKapasitas());
            data.put("kapasitasTersedia", lokasi.getKapasitasTersedia());
//            data.put("bukuList",  lokasi.getBukuList());
            return data;
        }).toList();

        return AppPage.create(listData, pageable, listLokasi.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var lokasi = lokasiRepository.findById(id).orElseThrow(() -> new RuntimeException("Data lokasi tidak ditemukan"));
        SimpleMap data = new SimpleMap();
        data.put("id", lokasi.getId());
        data.put("kodeLokasi", lokasi.getKodeLokasi());
        data.put("kapasitas", lokasi.getKapasitas());
        data.put("kapasitasTersedia", lokasi.getKapasitasTersedia());
        data.put("bukuList",  lokasi.getBukuList());
        return data;
    }
}
