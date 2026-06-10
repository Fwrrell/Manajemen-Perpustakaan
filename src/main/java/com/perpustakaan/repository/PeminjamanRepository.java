package com.perpustakaan.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.perpustakaan.model.Buku;
import com.perpustakaan.model.Member;
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

    public Peminjaman cariPeminjaman(int idMember, int idBuku) {
        String sql = "SELECT * FROM peminjaman WHERE id_member = ? AND id_buku = ? AND status_peminjaman = 'AKTIF'";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idMember);
            ps.setInt(2, idBuku);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return buatPeminjaman(rs);
            }

            return null;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new RuntimeException("Peminjaman tidak ditemukan " + e.getMessage());
        }
    }

    private Peminjaman buatPeminjaman(ResultSet rs) throws SQLException {
        int idPeminjaman = rs.getInt("id_peminjaman");
        int idMember = rs.getInt("id_member");
        int idBuku = rs.getInt("id_buku");
        LocalDate tglPinjam = rs.getObject("tanggal_peminjaman", LocalDate.class);
        LocalDate tglKembali = rs.getObject("tanggal_pengembalian", LocalDate.class);
        String status = rs.getString("status_peminjaman");

        Member member = new MemberRepository().cariMember(idMember);
        Buku buku = new BukuRepository().cariBuku(idBuku);

        Peminjaman p = new Peminjaman(idPeminjaman, member, buku, tglPinjam);

        if (tglKembali != null) {
            p.setPengembalian(tglKembali);
        }
        return p;
    }

    public boolean updatePengembalian(Peminjaman peminjaman) {
        String sql = "UPDATE peminjaman SET tanggal_pengembalian = ?, biaya_sewa = ?, denda = ?, "
                + "status_peminjaman = ? WHERE id_peminjaman = ?";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            double denda = peminjaman.hitungDendaKeterlambatan();
            double biayaSewa = peminjaman.getBukuDipinjam().getHargaSewa();
            String status = "";
            if (denda > 0) {
                status = "TERLAMBAT";
            } else {
                status = "DIKEMBALIKAN";
            }

            ps.setObject(1, peminjaman.getTanggalPengembalian());
            ps.setDouble(2, biayaSewa);
            ps.setDouble(3, denda);
            ps.setString(4, status);
            ps.setInt(5, peminjaman.getIdPeminjaman());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal update pengembalian buku: " + e.getMessage());
        }
    }
}
