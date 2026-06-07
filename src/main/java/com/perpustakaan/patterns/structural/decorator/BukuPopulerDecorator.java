package com.perpustakaan.patterns.structural.decorator;

import com.perpustakaan.model.Buku;

public class BukuPopulerDecorator extends BukuDecorator{
    public BukuPopulerDecorator(Buku buku) {
        super(buku);
    }
}
