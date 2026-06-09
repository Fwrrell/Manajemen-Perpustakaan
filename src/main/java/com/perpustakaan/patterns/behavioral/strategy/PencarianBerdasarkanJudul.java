package com.perpustakaan.patterns.behavioral.strategy;

import com.perpustakaan.model.Buku;
import java.util.ArrayList;
import java.util.List;

public class PencarianBerdasarkanJudul implements PencarianStrategy {
    @Override
    public List<Buku> pencarian(List<Buku> collection, String kataKunci) {
        List<Buku> hasilPencarian = new ArrayList<>();
        
        for (Buku buku : collection) {
            if (buku.getJudul() != null && buku.getJudul().toLowerCase().contains(kataKunci.toLowerCase())) {
                hasilPencarian.add(buku);
            }
        }
        return hasilPencarian;
    }
}