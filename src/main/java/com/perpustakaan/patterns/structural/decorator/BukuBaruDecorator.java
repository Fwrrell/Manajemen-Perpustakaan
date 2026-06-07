package com.perpustakaan.patterns.structural.decorator;

import com.perpustakaan.model.Buku;

public class BukuBaruDecorator extends BukuDecorator{
    public BukuBaruDecorator(Buku buku) {
        super(buku);
    }
}

