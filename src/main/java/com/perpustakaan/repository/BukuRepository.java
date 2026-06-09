package com.perpustakaan.repository;

import com.perpustakaan.model.Buku;
import com.perpustakaan.model.BukuFiksi;
import com.perpustakaan.model.BukuJurnal;
import com.perpustakaan.model.BukuPelajaran;
import com.perpustakaan.patterns.behavioral.state.StatusDipinjam;
import com.perpustakaan.patterns.behavioral.state.StatusRusak;
import com.perpustakaan.patterns.behavioral.state.StatusTersedia;
import com.perpustakaan.patterns.creational.singleton.KoneksiDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BukuRepository {

    public boolean simpanBuku(Buku buku) {
        String sql = "INSERT INTO buku (id_buku, judul, penulis, genre, tipe_buku, info_buku, maks_peminjaman, status_buku, is_langka, is_promo, is_laris) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 'TERSEDIA', 0, 0, 0)";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, buku.getIdBuku());
            pstmt.setString(2, buku.getJudul());
            pstmt.setString(3, buku.getPenulis());
            pstmt.setString(4, buku.getGenre());
            pstmt.setString(5, buku.getJenisBuku().toUpperCase());

            String infoBuku = null;
            if (buku instanceof BukuJurnal) {
                infoBuku = ((BukuJurnal) buku).getIdJurnal();
            } else if (buku instanceof BukuPelajaran) {
                infoBuku = ((BukuPelajaran) buku).getSubjek();
            }
            pstmt.setString(6, infoBuku);

            pstmt.setInt(7, buku.getDurasiHariPeminjaman());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Gagal menyimpan buku ke database: " + e.getMessage());
        }
    }

    public List<Buku> ambilBuku() {
        String sql = "SELECT * FROM buku";
        List<Buku> daftarBuku = new ArrayList<>();

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                daftarBuku.add(buatBuku(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data buku: " + e.getMessage());
        }
        return daftarBuku;
    }

    private Buku buatBuku(ResultSet rs) throws SQLException {
        String idBuku  = rs.getString("id_buku");
        String judul   = rs.getString("judul");
        String penulis = rs.getString("penulis");
        String genre   = rs.getString("genre");
        String tipe    = rs.getString("tipe_buku");   
        String info    = rs.getString("info_buku");   
        int maks       = rs.getInt("maks_peminjaman");
        String status  = rs.getString("status_buku");

        double hargaBeli = 0; // 

        Buku buku;
        switch (tipe) {
            case "FIKSI":
                buku = new BukuFiksi(idBuku, judul, penulis, genre, maks, hargaBeli);
                break;
            case "JURNAL":
                buku = new BukuJurnal(idBuku, judul, penulis, genre, maks, hargaBeli, info);
                break;
            case "PELAJARAN":
                buku = new BukuPelajaran(idBuku, judul, penulis, genre, maks, hargaBeli, info);
                break;
            default:
                throw new IllegalStateException("Tipe buku tidak dikenal: " + tipe);
        }

        switch (status) {
            case "DIPINJAM":
                buku.setStatusBuku(new StatusDipinjam());
                break;
            case "RUSAK":
                buku.setStatusBuku(new StatusRusak());
                break;
            default:
                buku.setStatusBuku(new StatusTersedia());
                break;
        }

        return buku;
    }
}