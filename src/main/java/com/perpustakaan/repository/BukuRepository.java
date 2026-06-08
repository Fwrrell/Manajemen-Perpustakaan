package com.perpustakaan.repository;

import com.perpustakaan.model.Buku;
import com.perpustakaan.model.BukuJurnal;
import com.perpustakaan.model.BukuPelajaran;
import com.perpustakaan.patterns.creational.singleton.KoneksiDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}