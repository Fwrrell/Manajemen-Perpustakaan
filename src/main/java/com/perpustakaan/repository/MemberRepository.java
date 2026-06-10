package com.perpustakaan.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
                idMember = rs.getInt("id_member");
                String nama = rs.getString("nama_lengkap");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                boolean isBlokir = rs.getBoolean("is_blokir");

                return new Member(nama, email, phone, idMember, isBlokir, new ArrayList<>(), new ArrayList<>());
            }

            return null;
        } catch (SQLException e) {
            // TODO: handle exception
            throw new RuntimeException("Member tidak ditemukan " + e.getMessage());
        }
    }
}
