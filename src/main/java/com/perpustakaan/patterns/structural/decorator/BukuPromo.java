package com.perpustakaan.patterns.structural.decorator;

import com.perpustakaan.model.Buku;

public class BukuPromo extends BukuDecorator{
    public BukuPromo(Buku buku) {
        super(buku);
    }

    @Override
    public double getHargaSewa() {
        // TODO Auto-generated method stub
        return super.getHargaSewa() / 2;
    }

    @Override
    public double getBiayaDenda() {
        // TODO Auto-generated method stub
        return super.getBiayaDenda() / 2;
    }
}
