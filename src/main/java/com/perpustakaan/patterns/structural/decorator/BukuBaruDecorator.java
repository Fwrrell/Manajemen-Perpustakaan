package com.perpustakaan.patterns.structural.decorator;

import com.perpustakaan.model.Buku;

public class BukuBaruDecorator extends BukuDecorator{
    public BukuBaruDecorator(Buku buku) {
        super(buku);
    }

    @Override
    public double getBiayaDenda() {
        // TODO Auto-generated method stub
        return super.getBiayaDenda() + buku.getHargaSewa() * 2;
    }
}

