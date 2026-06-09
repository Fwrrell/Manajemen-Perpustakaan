package com.perpustakaan.patterns.structural.decorator;

import com.perpustakaan.model.Buku;

public class BukuLangkaDecorator extends BukuDecorator {
    public BukuLangkaDecorator(Buku buku) {
        super(buku);
    }

   @Override
   public double getBiayaDenda() {
       // TODO Auto-generated method stub
       return super.getBiayaDenda() + buku.getHargaSewa() * 3;
   }
}
