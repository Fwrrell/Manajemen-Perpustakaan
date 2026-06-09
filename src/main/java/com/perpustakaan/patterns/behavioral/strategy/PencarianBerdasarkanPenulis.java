package com.perpustakaan.patterns.behavioral.strategy;

import com.perpustakaan.model.Buku;
import java.util.ArrayList;
import java.util.List;

public class PencarianBerdasarkanPenulis implements PencarianStrategy {
    @Override
    public List<Buku> pencarian(List<Buku> collection, String kataKunci) {
        List<Buku> hasilPencarian = new ArrayList<>();
        
        for (Buku buku : collection) {
            if (buku.getPenulis() != null && buku.getPenulis().toLowerCase().contains(kataKunci.toLowerCase())) {
                hasilPencarian.add(buku);
            }
        }
        return hasilPencarian;
    }
}