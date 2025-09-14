package com.sinaukoding.librarymanagementsystem.repository.master;

import com.sinaukoding.librarymanagementsystem.entity.master.Lokasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LokasiRepository extends JpaRepository<Lokasi, String>, JpaSpecificationExecutor<Lokasi> {
    Optional<Lokasi> findByIdAndKapasitasTersediaGreaterThan(String id, Integer kapasitasTersediaIsGreaterThan);

    boolean existsByKodeLokasi(String kodeLokasi);

    List<Lokasi> findByKapasitasTersediaGreaterThan(Integer kapasitasTersediaIsGreaterThan);
}
