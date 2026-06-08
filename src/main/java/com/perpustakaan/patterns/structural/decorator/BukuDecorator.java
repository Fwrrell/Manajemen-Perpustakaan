package com.perpustakaan.patterns.structural.decorator;
<<<<<<< HEAD

public class BukuDecorator {

=======

import com.perpustakaan.model.Buku;

public class BukuDecorator {
    protected Buku buku;

    public BukuDecorator(Buku buku) {
        this.buku = buku;
    }
    
    public int getBiayaDenda() {
        return 0;
    }
>>>>>>> c3ae36d0698eb0d571b666332d1bf85070c7b510
}
