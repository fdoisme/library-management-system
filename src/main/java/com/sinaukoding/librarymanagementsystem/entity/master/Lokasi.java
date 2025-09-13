package com.sinaukoding.librarymanagementsystem.entity.master;

import com.sinaukoding.librarymanagementsystem.entity.app.BaseEntity;
import jakarta.persistence.*;
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
@Table(name = "m_lokasi", indexes = {
        @Index(name = "idx_lokasi_created_date", columnList = "createdDate"),
        @Index(name = "idx_lokasi_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_lokasi_kode_lokasi", columnList = "kode_lokasi")
})
public class Lokasi extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String kodeLokasi;

    @Column(nullable = false)
    private Integer kapasitas;

    @Column(nullable = false)
    private Integer kapasitasTersedia;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lokasi", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Buku> bukuList = new ArrayList<>();
}
