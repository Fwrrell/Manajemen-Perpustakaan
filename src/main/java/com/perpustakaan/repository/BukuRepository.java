package com.perpustakaan.repository;

import com.perpustakaan.model.Buku;
import com.perpustakaan.model.BukuFiksi;
import com.perpustakaan.model.BukuJurnal;
import com.perpustakaan.model.BukuPelajaran;
import com.perpustakaan.patterns.behavioral.state.StatusDipinjam;
import com.perpustakaan.patterns.behavioral.state.StatusRusak;
import com.perpustakaan.patterns.behavioral.state.StatusTersedia;
import com.perpustakaan.patterns.creational.singleton.KoneksiDB;
import com.perpustakaan.patterns.structural.decorator.BukuLangkaDecorator;
import com.perpustakaan.patterns.structural.decorator.BukuPopulerDecorator;
import com.perpustakaan.patterns.structural.decorator.BukuPromo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BukuRepository {

    public boolean tambahBuku(Buku buku) {
        String sql = "INSERT INTO buku (judul, penulis, genre, tipe_buku, info_buku, maks_peminjaman, harga_beli, status_buku, is_langka, is_promo, is_laris) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 'TERSEDIA', 0, 0, 0)";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setString(3, buku.getGenre());
            ps.setString(4, buku.getJenisBuku().toUpperCase());

            String infoBuku = null;
            if (buku instanceof BukuJurnal) {
                infoBuku = ((BukuJurnal) buku).getIdJurnal();
            } else if (buku instanceof BukuPelajaran) {
                infoBuku = ((BukuPelajaran) buku).getSubjek();
            }
            ps.setString(5, infoBuku);

            ps.setInt(6, buku.getDurasiHariPeminjaman());
            ps.setDouble(7, buku.getHargaBeli());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Gagal menyimpan buku ke database: " + e.getMessage());
        }
    }

    public boolean tandaiLangka(int idBuku, boolean langka) {
        String sql = "UPDATE buku SET is_langka = ? WHERE id_buku = ?";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, langka);
            ps.setInt(2, idBuku);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengubah buku menjadi langka" + e.getMessage());
        }
    }

    public boolean tandaiPopuler(int idBuku, boolean populer) {
        String sql = "UPDATE buku SET is_laris = ? WHERE id_buku = ?";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, populer);
            ps.setInt(2, idBuku);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengubah buku menjadi langka" + e.getMessage());
        }
    }

    public boolean tandaiPromo(int idBuku, boolean promo) {
        String sql = "UPDATE buku SET is_promo = ? WHERE id_buku = ?";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, promo);
            ps.setInt(2, idBuku);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengubah buku menjadi langka" + e.getMessage());
        }
    }

    public Buku cariBuku(int idBuku) {
        String sql = "SELECT * FROM buku WHERE id_buku = ?";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBuku);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return buatBuku(rs);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Buku tidak terdaftar di Database" + e.getMessage());
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

    public boolean updateStatusBuku(int idBuku, String status) {
        String sql = "UPDATE buku SET status_buku = ? WHERE id_buku = ?";
        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, idBuku);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal update status buku: " + e.getMessage());
        }
    }

    private Buku buatBuku(ResultSet rs) throws SQLException {
        int idBuku = rs.getInt("id_buku");
        String judul = rs.getString("judul");
        String penulis = rs.getString("penulis");
        String genre = rs.getString("genre");
        String tipe = rs.getString("tipe_buku");
        String info = rs.getString("info_buku");
        int maks = rs.getInt("maks_peminjaman");
        String status = rs.getString("status_buku");
        boolean langka = rs.getBoolean("is_langka");
        boolean promo = rs.getBoolean("is_promo");
        boolean populer = rs.getBoolean("is_laris");

        double hargaBeli = rs.getDouble("harga_beli");

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

        if (langka) {
            buku = new BukuLangkaDecorator(buku);
        }
        if (populer) {
            buku = new BukuPopulerDecorator(buku);
        }
        if (promo) {
            buku = new BukuPromo(buku);
        }

        return buku;
    }
}