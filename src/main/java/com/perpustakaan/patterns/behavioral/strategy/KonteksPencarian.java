package com.perpustakaan.patterns.behavioral.strategy;

import java.util.List;

import com.perpustakaan.model.Buku;

public class KonteksPencarian {
     private PencarianStrategy pencarian;

    public KonteksPencarian(PencarianStrategy strategy) {
        this.pencarian = strategy;
    }

    public void setStrategy(PencarianStrategy strategy) {
        this.pencarian = strategy;
    }

    public List<Buku> executePencarian(List<Buku> collection, String kataKunci) {
        return pencarian.pencarian(collection, kataKunci);
    }
}
