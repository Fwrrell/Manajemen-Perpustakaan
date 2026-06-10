package com.perpustakaan.patterns.behavioral.strategy;

import java.util.List;

import com.perpustakaan.model.Buku;

public interface PencarianStrategy {
    List<Buku> pencarian(List<Buku> collection, String kataKunci);
}
