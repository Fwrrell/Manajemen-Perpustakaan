package com.perpustakaan.patterns.structural.decorator;

import com.perpustakaan.model.Buku;

public class BukuPopulerDecorator extends BukuDecorator{
    public BukuPopulerDecorator(Buku buku) {
        super(buku);
    }

    @Override
    public double getBiayaDenda() {
        // TODO Auto-generated method stub
        return super.getBiayaDenda() + buku.getHargaSewa() * 3;
    }
}
