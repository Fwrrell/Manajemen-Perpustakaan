package com.perpustakaan.patterns.structural.facade;

import java.time.LocalDate;
import java.util.List;

import com.perpustakaan.model.Buku;
import com.perpustakaan.patterns.behavioral.strategy.KonteksPencarian;
import com.perpustakaan.patterns.behavioral.strategy.PencarianBerdasarkanGenre;
import com.perpustakaan.patterns.behavioral.strategy.PencarianBerdasarkanJudul;
import com.perpustakaan.patterns.behavioral.strategy.PencarianBerdasarkanPenulis;
import com.perpustakaan.patterns.creational.factory.BukuFactory;
import com.perpustakaan.repository.BukuRepository;
import com.perpustakaan.repository.MemberRepository;
import com.perpustakaan.repository.PeminjamanRepository;
import com.perpustakaan.model.Member;
import com.perpustakaan.model.Peminjaman;

public class PerpustakaanFacade {
    KonteksPencarian pencarian;

    public PerpustakaanFacade() {
        pencarian = new KonteksPencarian(new PencarianBerdasarkanJudul());
    }

    public boolean RegistrasiMember(String nama, String email, String noTelpon) {
        return new MemberRepository().tambahMember(nama, email, noTelpon, false);
    }

    public boolean tambahBuku(String tipe, String judul, String penulis, String genre, int maksPeminjamanm,
            double hargaBeli, String info) {
        Buku newBuku = new BukuFactory().buatBuku(tipe, judul, penulis, genre, maksPeminjamanm, hargaBeli, null);
        return new BukuRepository().tambahBuku(newBuku);
    }

    public Buku tandaiBukuLangka(int idBuku, boolean langka) {
        BukuRepository x = new BukuRepository();
        x.tandaiLangka(idBuku, langka);

        return x.cariBuku(idBuku);
    }

    public Buku tandaiBukuPopouler(int idBuku, boolean populer) {
        BukuRepository x = new BukuRepository();
        x.tandaiPopuler(idBuku, populer);

        return x.cariBuku(idBuku);
    }

    public Buku tandaiBukuPromo(int idBuku, boolean promo) {
        BukuRepository x = new BukuRepository();
        x.tandaiPromo(idBuku, promo);

        return x.cariBuku(idBuku);
    }

    public Peminjaman pinjamBuku(int idPengguna, int idBuku) {
        Member member = new MemberRepository().cariMember(idPengguna);
        Buku buku = new BukuRepository().cariBuku(idBuku);

        if (member == null || buku == null) {
            return null;
        }
        if (member.isBlocked()) {
            return null;
        }
        if (!buku.getStatusBuku().getStatusBuku().equals("Tersedia")) {
            return null;
        }

        buku.pinjam();
        new BukuRepository().updateStatusBuku(idBuku, "DIPINJAM");

        Peminjaman peminjaman = new Peminjaman(-1, member, buku, LocalDate.now());
        new PeminjamanRepository().tambahPeminjaman(peminjaman);

        return peminjaman;
    }

    public List<Buku> pencarianBuku(String kriteria, String kataKunci) {
        switch (kriteria.toLowerCase()) {
            case "judul":
                pencarian.setStrategy(new PencarianBerdasarkanJudul());
                break;
            case "penulis":
                pencarian.setStrategy(new PencarianBerdasarkanPenulis());
                break;
            case "genre":
                pencarian.setStrategy(new PencarianBerdasarkanGenre());
                break;
        }

        return pencarian.executePencarian(new BukuRepository().ambilBuku(), kataKunci);
    }

    public List<Member> ambilSemuaMember() {
        return new MemberRepository().getAllMember();
    }

    public boolean updateMember(Member m) {
        return new MemberRepository().updateMember(m);
    }

    public boolean ubahStatusBlokirMember(int idMember, boolean status) {
        return new MemberRepository().ubahStatusBlokir(idMember, status);
    }

    public boolean hapusMember(int idMember) {
        return new MemberRepository().hapusMember(idMember);
    }

    public Member cariMember(int idMember) {
        return new MemberRepository().cariMember(idMember);
    }
}
