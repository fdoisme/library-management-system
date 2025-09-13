package com.sinaukoding.librarymanagementsystem.model.enums;

import lombok.Getter;

@Getter
public enum Kategori {
    NOVEL("Novel"),
    ENSIKLOPEDIA("Ensiklopedia"),
    MAJALAH("Majalah"),
    KOMIK("Komik"),
    BUKU_PELAJARAN("Buku Pelajaran"),
    KAMUS("Kamus"),
    BIOGRAFI("Biografi"),
    ILMU_PENGETAHUAN("Ilmu Pengetahuan"),;

    private final String label;

    Kategori(String label) {
        this.label = label;
    }

}
