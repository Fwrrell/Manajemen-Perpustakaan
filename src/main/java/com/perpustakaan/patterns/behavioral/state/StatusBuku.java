package main.behavioral.state;

import main.Model.Buku;

public interface StatusBuku {
    public void pinjam(Buku buku);

    public void kembalikan(Buku buku);

    public void tandaiKerusakan(Buku buku);

    public String getStatusBuku();
    
}
