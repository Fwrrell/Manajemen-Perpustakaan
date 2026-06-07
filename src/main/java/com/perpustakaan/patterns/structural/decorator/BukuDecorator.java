package com.perpustakaan.patterns.structural.decorator;

import com.perpustakaan.model.Buku;

public class BukuDecorator {
    protected Buku buku;

    public BukuDecorator(Buku buku) {
        this.buku = buku;
    }
    
    public int getBiayaDenda() {
        return 0;
    }
}
