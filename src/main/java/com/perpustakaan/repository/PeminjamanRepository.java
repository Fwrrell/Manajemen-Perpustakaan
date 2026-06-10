package com.perpustakaan.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.perpustakaan.model.Peminjaman;
import com.perpustakaan.patterns.creational.singleton.KoneksiDB;

public class PeminjamanRepository {
    public int pinjamanAktifMember(int idMember) {
        String sql = "SELECT COUNT(*) FROM peminjaman WHERE id_member = ?";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idMember);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menghitung pinjaman aktif: " + e.getMessage());
        }
        return 0;
    }

    public boolean tambahPeminjaman(Peminjaman peminjaman, double deposit) {
        String sql = "INSERT INTO peminjaman (id_member, id_buku, tanggal_peminjaman, tanggal_jatuh_tempo, biaya_sewa, biaya_deposit, status_peminjaman) VALUES"
                +
                "(?, ?, ?, ?, 0, ?, 'AKTIF')";
        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, peminjaman.getMember().getIdMember());
            ps.setInt(2, peminjaman.getBukuDipinjam().getIdBuku());
            ps.setObject(3, peminjaman.getTanggalPeminjaman());
            ps.setObject(4, peminjaman.getTanggalAkhirPeminjaman());
            ps.setObject(5, deposit);

            int affected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                peminjaman.setIdPeminjaman(rs.getInt(1));
            }

            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Peminjaman gagal ditambahkan " + e.getMessage());
        }
    }

    public int hitungPeminjamAktif() {
        int total = 0;
        String sql = "SELECT COUNT(DISTINCT id_member) AS total FROM peminjaman WHERE status_peminjaman = 'AKTIF'";
        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghitung peminjam aktif: " + e.getMessage());
        }
        return total;
    }
}
