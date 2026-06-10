package com.perpustakaan.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.perpustakaan.model.Peminjaman;
import com.perpustakaan.patterns.creational.singleton.KoneksiDB;

public class PeminjamanRepository {
    public boolean tambahPeminjaman(Peminjaman peminjaman) {
        String sql = "INSERT INTO peminjaman (id_member, id_buku, tanggal_peminjaman, tanggal_jatuh_tempo, status_peminjaman) VALUES" +
                "(?, ?, ?, ?, 'AKTIF')";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, peminjaman.getMember().getIdMember());
            ps.setInt(2, peminjaman.getBukuDipinjam().getIdBuku());
            ps.setObject(3, peminjaman.getTanggalPeminjaman());
            ps.setObject(4, peminjaman.getTanggalAkhirPeminjaman());

            int affected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                peminjaman.setIdPeminjaman(rs.getInt(1));
            }

            return affected > 0;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new RuntimeException("Peminjaman gagal ditambahkan " + e.getMessage());
        }
    }
}
