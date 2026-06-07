package main.behavioral.state;

import main.Model.Buku;

public class StatusTersedia implements StatusBuku{
    @Override
    public void pinjam(Buku buku) {
        // TODO Auto-generated method stub
        System.out.println("Buku " + buku.getJudul() + " berhasil dipinjam");
        buku.setStatusBuku(new StatusDipinjam());
    }

    @Override
    public void kembalikan(Buku buku) {
        // TODO Auto-generated method stub
        System.out.println("Buku " + buku.getJudul() + " sedang tidak dipinjam");
    }

    @Override
    public void tandaiKerusakan(Buku buku) {
        // TODO Auto-generated method stub
        System.out.println("Buku " + buku.getJudul() + " dalam kondisi rusak");
        buku.setStatusBuku(new StatusRusak());
    }
    
    @Override
    public String getStatusBuku() {
        // TODO Auto-generated method stub
        return "Tersedia";
    }
}
