package com.perpustakaan.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.perpustakaan.model.Member;
import com.perpustakaan.patterns.creational.singleton.KoneksiDB;

public class MemberRepository {
    public boolean tambahMember(String nama, String email, String noTelpon, boolean isBlokir) {
        String sql = "INSERT INTO members (nama_lengkap, email, phone, is_blokir) "
                +
                "VALUES (?, ?, ?, ?)";
        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nama);
            pstmt.setString(2, email);
            pstmt.setString(3, noTelpon);
            pstmt.setBoolean(4, isBlokir);

            int affected = pstmt.executeUpdate();

            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menambahkan member baru ke database: " + e.getMessage());
        }
    }

    public Member cariMember(int idMember) {
        String sql = "SELECT * FROM members WHERE id_member = ?";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idMember);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToMember(rs);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Member tidak ditemukan " + e.getMessage());
        }
    }

    public List<Member> getAllMember() {
        List<Member> listMember = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                listMember.add(mapResultSetToMember(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil semua data member: " + e.getMessage());
        }
        return listMember;
    }

    public boolean updateMember(Member member) {
        String sql = "UPDATE members SET nama_lengkap = ?, email = ?, phone = ? WHERE id_member = ?";
        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, member.getNama());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getNoTelpon());
            pstmt.setInt(4, member.getIdMember());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengupdate data member: " + e.getMessage());
        }
    }

    public boolean ubahStatusBlokir(int idMember, boolean status) {
        String sql = "UPDATE members SET is_blokir = ? WHERE id_member = ?";
        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setBoolean(1, status);
            pstmt.setInt(2, idMember);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengubah status blokir member: " + e.getMessage());
        }
    }

    public boolean hapusMember(int idMember) {
        String sql = "DELETE FROM members WHERE id_member = ?";
        try {
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idMember);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menghapus member: " + e.getMessage());
        }
    }

    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        int idMember = rs.getInt("id_member");
        String nama = rs.getString("nama_lengkap");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        boolean isBlokir = rs.getBoolean("is_blokir");

        return new Member(nama, email, phone, idMember, isBlokir, new ArrayList<>(), new ArrayList<>());
    }
}
