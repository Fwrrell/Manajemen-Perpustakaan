package com.perpustakaan.patterns.structural.facade;

import java.util.List;

import com.perpustakaan.model.Buku;
import com.perpustakaan.patterns.behavioral.strategy.KonteksPencarian;
import com.perpustakaan.patterns.behavioral.strategy.PencarianBerdasarkanGenre;
import com.perpustakaan.patterns.behavioral.strategy.PencarianBerdasarkanJudul;
import com.perpustakaan.patterns.behavioral.strategy.PencarianBerdasarkanPenulis;
import com.perpustakaan.repository.BukuRepository;

public class PerpustakaanFacade {
    KonteksPencarian pencarian;

    public PerpustakaanFacade() {

    }
    
    public List<Buku> pencarianBuku(String kriteria, String kataKunci) {
        switch (kriteria.toLowerCase()) {
            case "judul":
                pencarian.setStrategy(new PencarianBerdasarkanJudul());
                break;
            case "penulis":
                pencarian.setStrategy(new PencarianBerdasarkanPenulis());
                break;
            case "genre":
                pencarian.setStrategy(new PencarianBerdasarkanGenre());
                break;
        }

        return pencarian.executePencarian(new BukuRepository().ambilBuku(), kataKunci);
    }
}
