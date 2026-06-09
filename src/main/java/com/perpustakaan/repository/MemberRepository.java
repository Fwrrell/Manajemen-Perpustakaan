package com.perpustakaan.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.perpustakaan.model.Member;
import com.perpustakaan.patterns.creational.singleton.KoneksiDB;

public class MemberRepository {
    public boolean tambahMember(Member member) {
        String sql = "INSERT INTO member (nama_lengkap, email, phone, is_blokir) "
                +
                "VALUES (?, ?, ?, ?)";
        try{
            Connection conn = KoneksiDB.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, member.getNama());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getNoTelpon());
            pstmt.setBoolean(4, member.isBlocked());
            
            int affected = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            
            if (rs.next()) {
                int id = rs.getInt(1);
                member.setIdPengguna(id);
            }

            return affected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menambahkan member baru ke database: " + e.getMessage());
        }
    }
}
