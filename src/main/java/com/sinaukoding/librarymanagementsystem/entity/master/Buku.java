package com.sinaukoding.librarymanagementsystem.entity.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinaukoding.librarymanagementsystem.entity.app.BaseEntity;
import com.sinaukoding.librarymanagementsystem.model.enums.Kategori;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_buku", indexes = {
        @Index(name = "idx_buku_created_date", columnList = "createdDate"),
        @Index(name = "idx_buku_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_buku_judul", columnList = "judul"),
        @Index(name = "idx_buku_kategori", columnList = "kategori"),
        @Index(name = "idx_buku_tahun_publikasi", columnList = "tahun_publikasi"),
        @Index(name = "idx_buku_pengarang", columnList = "pengarang")
})
public class Buku extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String judul;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String deskripsi;

    @Column(nullable = false)
    private String pengarang;

    @Column(nullable = false)
    private String penerbit;

    @Min(value = 0, message = "Tahun tidak boleh negatif")
    @Column(nullable = false)
    private Integer tahunPublikasi;

    private String sampulBukuUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Kategori kategori;

    @Column(nullable = false)
    private String tags;

    @ManyToOne(fetch = FetchType.LAZY)
//    Boleh null karena asumsi jika dipinjam maka lokasi tidak ada
    @JoinColumn(name = "id_Lokasi", nullable = true)
    @JsonIgnore
    private Lokasi lokasi;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buku", orphanRemoval = true, fetch = FetchType.LAZY)
//    @Builder.Default
//    private List<Peminjaman> peminjamanList = new ArrayList<>();
}
