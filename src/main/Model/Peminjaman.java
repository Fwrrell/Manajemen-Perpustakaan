package main.Model;

public class Peminjaman {
    private String idPeminjaman;
    private Member pengguna;
    private Buku bukuDipinjam;
    private String tanggalPeminjaman;
    private String tanggalPengembalian;
    private double biayaDenda;
    private double jumlahDibayar;
    private boolean statusPeminjaman;

    public Peminjaman(String idPeminjaman, Member pengguna, Buku bukuDipinjam, String tanggalPeminjaman,
            String tanggalPengembalian, double biayaDenda, double jumlahDibayar, boolean statusPeminjaman) {
        this.idPeminjaman = idPeminjaman;
        this.pengguna = pengguna;
        this.bukuDipinjam = bukuDipinjam;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.tanggalPengembalian = tanggalPengembalian;
        this.biayaDenda = biayaDenda;
        this.jumlahDibayar = jumlahDibayar;
        this.statusPeminjaman = statusPeminjaman;
    }
    
    // public double HitungDendaKeterlambatan() {

    // }
    
    public void ubahStatusPeminjaman() {
        statusPeminjaman = false;
    }

    public boolean getStatusPeminjaman() {
        return this.statusPeminjaman;
    }

    // public boolean melebihiBatasWaktu() {
        
    // }
    
}
