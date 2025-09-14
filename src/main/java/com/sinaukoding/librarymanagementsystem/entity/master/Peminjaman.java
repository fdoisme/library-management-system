package com.sinaukoding.librarymanagementsystem.entity.master;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinaukoding.librarymanagementsystem.entity.app.BaseEntity;
import com.sinaukoding.librarymanagementsystem.entity.managementuser.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_peminjaman", indexes = {
        @Index(name = "idx_peminjaman_created_date", columnList = "createdDate"),
        @Index(name = "idx_peminjaman_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_peminjaman_id_user", columnList = "id_user"),
        @Index(name = "idx_peminjaman_id_buku", columnList = "id_buku"),
        @Index(name = "idx_peminjaman_return_date", columnList = "return_date"),
        @Index(name = "idx_peminjaman_due_date", columnList = "due_date")
})
public class Peminjaman extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(columnDefinition = "timestamp with time zone", nullable = false)
    private LocalDateTime dueDate;

    @Column(columnDefinition = "timestamp with time zone")
    private LocalDateTime returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_buku", nullable = false)
    @JsonIgnore
    private Buku buku;
}
