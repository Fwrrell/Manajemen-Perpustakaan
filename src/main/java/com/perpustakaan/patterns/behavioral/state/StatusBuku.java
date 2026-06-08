package com.perpustakaan.patterns.behavioral.state;

import com.perpustakaan.model.*;;

public interface StatusBuku {
    public void pinjam(Buku buku);

    public void kembalikan(Buku buku);

    public void tandaiKerusakan(Buku buku);

    public String getStatusBuku();

}
