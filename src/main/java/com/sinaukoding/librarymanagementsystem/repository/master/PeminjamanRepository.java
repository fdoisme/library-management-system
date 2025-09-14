package com.sinaukoding.librarymanagementsystem.repository.master;

import com.sinaukoding.librarymanagementsystem.entity.master.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;

public interface PeminjamanRepository extends JpaRepository<Peminjaman, String>, JpaSpecificationExecutor<Peminjaman> {

    boolean existsByUserIdAndReturnDateIsNull(String userId);
}
